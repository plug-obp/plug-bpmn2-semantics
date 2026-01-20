package plug.bpmn2.semantics.transition.utils;

import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.*;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.CollaborationInstance;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.state.instance.ProcessInstance;
import plug.bpmn2.semantics.state.instance.TaskInstance;
import plug.bpmn2.semantics.state.instance.data.ActivityState;
import plug.bpmn2.semantics.state.instance.data.Token;
import plug.bpmn2.semantics.state.instance.impl.CollaborationInstanceImpl;
import plug.bpmn2.semantics.state.instance.impl.ProcessInstanceImpl;
import plug.bpmn2.semantics.state.instance.impl.TaskInstanceImpl;

import java.util.Collection;
import java.util.List;

public class ElementsOpen {

    static public void addInstance(BPMNRuntimeState state,
                                   BPMNRuntimeInstance scope,
                                   BPMNRuntimeInstance instance) {
        Collection<BPMNRuntimeInstance> targetCollection =
                scope == null ?
                        state.getRootInstanceList() :
                        scope.getChildInstanceList();
        targetCollection.add(instance);
    }

    static public int countInstances(BPMNRuntimeState state, BPMNRuntimeInstance scope, BaseElement baseElement) {
        int result = 0;
        Collection<BPMNRuntimeInstance> instances = scope == null ? state.getRootInstanceList() : scope.getChildInstanceList();
        for (BPMNRuntimeInstance instance : instances) {
            if (instance.getBaseElement().equals(baseElement)) {
                result += 1;
            }
        }
        return result;
    }

    static public boolean openCollaboration(BPMNModelHandler model,
                                            BPMNRuntimeState state,
                                            Collaboration collaboration,
                                            boolean execute) {
        for (BPMNRuntimeInstance rootInstance : state.getRootInstanceList()) {
            if (rootInstance.getBaseElement() == collaboration) {
                return false;
            }
        }
        if (execute) {
            CollaborationInstance instance = new CollaborationInstanceImpl(null, collaboration);
            addInstance(state, null, instance);
            collaboration.getParticipants().stream()
                    .map(Participant::getProcessRef)
                    .forEach(process -> openProcess(model, state, instance, process, true));
        }
        return true;
    }

    static public void proceedStartEvent(BPMNModelHandler model,
                                         BPMNRuntimeState state,
                                         ProcessInstance processInstance,
                                         StartEvent startEvent) {
        if (startEvent.getIncoming().isEmpty() && startEvent.getIncomingConversationLinks().isEmpty()) {
            List<SequenceFlow> outgoings = startEvent.getOutgoing();
            for (SequenceFlow outgoing : outgoings) {
                Token token = model.tokens.get(outgoing);
                processInstance.getTokenSet().add(token);
            }
        } else if (!startEvent.getIncoming().isEmpty()) {
            throw new UnsupportedOperationException("Incoming sequence flow to a StartEvent");
        } else {
            throw new UnsupportedOperationException("Conversation Link to a a StartEvent");
        }
    }

    static public boolean openProcess(BPMNModelHandler model,
                                      BPMNRuntimeState state,
                                      BPMNRuntimeInstance scope,
                                      Process process,
                                      boolean execute) {
        final ProcessInstance processInstance = new ProcessInstanceImpl(scope, process);
        process.getFlowElements().stream()
                .filter(flowElement -> flowElement instanceof StartEvent)
                .forEach(flowElement -> {
                    StartEvent startEvent = (StartEvent) flowElement;
                    proceedStartEvent(model, state, processInstance, startEvent);
                });
        if (processInstance.getTokenSet().isEmpty()) return false;
        if (execute) {
            addInstance(state, scope, processInstance);
        }
        return true;
    }

    static public boolean openTask(BPMNModelHandler model,
                                   BPMNRuntimeState state,
                                   FlowElementsContainerInstance scope,
                                   Task task,
                                   boolean execute) {
        Collection<Token> incomingTokens = model.tokens.get(task.getIncoming());
        boolean hasIncoming = false;
        for (Token token : incomingTokens) {
            if (scope.getTokenSet().contains(token)) {
                if (execute) {
                    scope.getTokenSet().remove(token);
                }
                hasIncoming = true;
            }
        }
        if (!hasIncoming) return false;
        if (execute) {
            TaskInstance taskInstance = new TaskInstanceImpl(scope, task, ActivityState.ACTIVE);
            addInstance(state, scope, taskInstance);
        }
        return true;
    }

    static public boolean open(BPMNModelHandler model,
                               BPMNRuntimeState state,
                               BPMNRuntimeInstance scope,
                               BaseElement element,
                               boolean execute) {
        if (countInstances(state, scope, element) > 0) return false;
        if (element instanceof Choreography) {
            throw new UnsupportedOperationException("Open Choreography");
        }
        if (element instanceof Collaboration) {
            return openCollaboration(model, state, (Collaboration) element, execute);
        }
        if (element instanceof SubProcess) {
            throw new UnsupportedOperationException("Open SubProcess");
        }
        if (element instanceof org.eclipse.bpmn2.Process) {
            return openProcess(model, state, scope, (Process) element, execute);
        }
        if (element instanceof Task) {
            FlowElementsContainerInstance parentScope = (FlowElementsContainerInstance) scope;
            return openTask(model, state, parentScope, (Task) element, execute);
        }
        throw new UnsupportedOperationException("Open " + element);
    }

}
