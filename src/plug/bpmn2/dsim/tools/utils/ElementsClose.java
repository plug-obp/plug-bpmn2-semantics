package plug.bpmn2.dsim.tools.utils;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collection;

public class ElementsClose {

    static public void removeInstance(BPMNRuntimeState state,
                                      BPMNRuntimeInstance instance) {
        BPMNRuntimeInstance parent = instance.getParent();
        Collection<BPMNRuntimeInstance> targetCollection =
                parent == null ?
                        state.getRootInstanceList() :
                        parent.getChildInstanceList();
        targetCollection.remove(instance);
    }

    static public boolean closeProcess(BPMNModelHandler model,
                                       BPMNRuntimeState state,
                                       ProcessInstance processInstance,
                                       boolean execute) {
        if (processInstance.getTokenSet().isEmpty() &&
                processInstance.getChildInstanceList().isEmpty()) {
            if (execute) {
                removeInstance(state, processInstance);
            }
        }
        return false;
    }

    static public boolean closeTask(BPMNModelHandler model,
                                    TaskInstance taskInstance,
                                    boolean execute) {
        FlowElementsContainerInstance parentInstance = taskInstance.getParent();
        if (!parentInstance.getChildInstanceList().contains(taskInstance)) return false;
        if (execute) {
            parentInstance.getChildInstanceList().remove(taskInstance);
            Collection<SequenceFlow> outgoingSequenceFlows = taskInstance.getBaseElement().getOutgoing();
            Collection<Token> outgoingTokens = model.tokens.get(outgoingSequenceFlows);
            parentInstance.getTokenSet().addAll(outgoingTokens);
        }
        return true;
    }

    static public boolean close(BPMNModelHandler model,
                                BPMNRuntimeState state,
                                BPMNRuntimeInstance instance,
                                boolean execute
    ) {
        if (instance instanceof ChoreographyInstance) {
            return false;
        }
        if (instance instanceof CollaborationInstance) {
            return false;
        }
        if (instance instanceof SubProcessInstance) {
            throw new UnsupportedOperationException("Close SubProcessInstance");
        }
        if (instance instanceof ProcessInstance) {
            return closeProcess(model, state, (ProcessInstance) instance, execute);
        }
        if (instance instanceof TaskInstance) {
            return closeTask(model, (TaskInstance) instance, execute);
        }
        throw new UnsupportedOperationException("Close " + instance);
    }

}
