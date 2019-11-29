package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.*;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.CollaborationInstance;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;
import plug.bpmn2.interpretation.model.instance.TaskInstance;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.interpretation.model.instance.impl.CollaborationInstanceImpl;
import plug.bpmn2.interpretation.model.instance.impl.ProcessInstanceImpl;
import plug.bpmn2.interpretation.model.instance.impl.TaskInstanceImpl;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collection;
import java.util.List;

class ElementsOpen {

    static public void addInstance(BPMNRuntimeState state,
                                   BPMNRuntimeInstance scope,
                                   BPMNRuntimeInstance instance) {
        Collection<BPMNRuntimeInstance> targetCollection =
                scope == null ?
                        state.getRootInstanceList() :
                        scope.getChildInstanceList();
        targetCollection.add(instance);
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

    static public boolean isUnconditionalStartEvent(FlowElement flowElement) {
        if (!(flowElement instanceof StartEvent)) return false;
        StartEvent startEvent = (StartEvent) flowElement;
        return startEvent.getIncoming().isEmpty();
    }

    static public boolean openProcess(BPMNModelHandler model,
                                      BPMNRuntimeState state,
                                      BPMNRuntimeInstance scope,
                                      Process process,
                                      boolean execute) {
        final ProcessInstance processInstance = new ProcessInstanceImpl(scope, process);
        process.getFlowElements().stream()
                .filter(flowElement -> isUnconditionalStartEvent(flowElement))
                .forEach(flowElement -> {
                    StartEvent startEvent = (StartEvent) flowElement;
                    List<SequenceFlow> outgoings = startEvent.getOutgoing();
                    for (SequenceFlow outgoing : outgoings) {
                        Token token = model.tokens.get(outgoing);
                        processInstance.getTokenSet().add(token);
                    }
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
