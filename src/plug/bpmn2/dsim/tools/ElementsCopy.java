package plug.bpmn2.dsim.tools;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;
import plug.bpmn2.interpretation.model.instance.impl.CollaborationInstanceImpl;
import plug.bpmn2.interpretation.model.instance.impl.ProcessInstanceImpl;
import plug.bpmn2.interpretation.model.instance.impl.TaskInstanceImpl;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collection;

class ElementsCopy {

    static public void copyChildInstances(BPMNModelHandler model,
                                          BPMNRuntimeState state,
                                          BPMNRuntimeInstance instance,
                                          BPMNRuntimeInstance result) {
        for (BPMNRuntimeInstance childInstance : instance.getChildInstanceSet()) {
            copyInstance(model, state, childInstance, result);
        }
    }

    static public void addNewInstance(BPMNRuntimeState state,
                                      BPMNRuntimeInstance newParent,
                                      BPMNRuntimeInstance result) {
        Collection<BPMNRuntimeInstance> targetCollection = newParent == null ?
                state.getRootInstances() :
                newParent.getChildInstanceSet();
        targetCollection.add(result);
    }

    static public void copyInstance(BPMNModelHandler model,
                                    BPMNRuntimeState state,
                                    BPMNRuntimeInstance instance,
                                    BPMNRuntimeInstance newParent) {
        if (instance instanceof ChoreographyInstance) {
            throw new UnsupportedOperationException("Copy Choreography");
        }
        if (instance instanceof CollaborationInstance) {
            CollaborationInstance collaborationInstance = (CollaborationInstance) instance;
            CollaborationInstance result = new CollaborationInstanceImpl(
                    newParent,
                    collaborationInstance.getBaseElement()
            );
            copyChildInstances(model, state, instance, result);
            addNewInstance(state, newParent, result);
            return;
        }
        if (instance instanceof SubProcessInstance) {
            throw new UnsupportedOperationException("Copy SubProcessInstance");
        }
        if (instance instanceof ProcessInstance) {
            ProcessInstance processInstance = (ProcessInstance) instance;
            ProcessInstance result = new ProcessInstanceImpl(
                    newParent,
                    processInstance.getBaseElement()
            );
            result.getTokenSet().addAll(processInstance.getTokenSet());
            copyChildInstances(model, state, instance, result);
            addNewInstance(state, newParent, result);
            return;
        }
        if (instance instanceof TaskInstance) {
            if (!(newParent instanceof FlowElementsContainerInstance)) {
                throw new IllegalArgumentException("A task parent has to be a flow container");
            }
            TaskInstance taskInstance = (TaskInstance) instance;
            TaskInstance result = new TaskInstanceImpl(
                    (FlowElementsContainerInstance) newParent,
                    taskInstance.getBaseElement(),
                    taskInstance.getActivityState()
            );
            copyChildInstances(model, state, instance, result);
            addNewInstance(state, newParent, result);
            return;
        }
        throw new UnsupportedOperationException("Copy " + instance);
    }

    static public BPMNRuntimeState copy(BPMNModelHandler model,
                                        BPMNRuntimeState state) {
        BPMNRuntimeState result = new BPMNRuntimeState();
        for (BPMNRuntimeInstance instance : state.getRootInstances()) {
            copyInstance(model, result, instance, null);
        }
        return result;
    }

}
