package plug.bpmn2.interpretation.model.utils;

import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
import plug.bpmn2.interpretation.model.instance.impl.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BPMNRuntimeCopy implements BPMNInstanceVisitor {

    Map<BPMNRuntimeInstance, BPMNRuntimeInstance> copyMap = new HashMap<>();

    public BPMNRuntimeState copy(BPMNRuntimeState state) {
        BPMNRuntimeState copy = new BPMNRuntimeState();
        for (BPMNRuntimeInstance instance : state.getRootInstanceList()) {
            BPMNRuntimeInstance instanceCopy = get(instance);
            copy.getRootInstanceList().add(instanceCopy);
        }
        for (MessageFlowData messageFlowData : state.getMessageFlowDataList()) {
            MessageFlowData messageFlowDataCopy = new MessageFlowData(
                    messageFlowData.getBaseElement(),
                    copyMap.get(messageFlowData.getSourceParent()),
                    copyMap.get(messageFlowData.getTargetParent()),
                    messageFlowData.getData()
            );
            copy.getMessageFlowDataList().add(messageFlowDataCopy);
        }
        return copy;
    }

    private LinkedList<BPMNRuntimeInstance> parentStack = new LinkedList<>();
    private BPMNRuntimeInstance result;

    private BPMNRuntimeInstance getParent() {
        if (parentStack.size() == 0) {
            return null;
        }
        return parentStack.getLast();
    }

    private FlowElementsContainerInstance getActivityParent() {
        BPMNRuntimeInstance parent = getParent();
        return (FlowElementsContainerInstance) parent;
    }

    public BPMNRuntimeInstance get(BPMNRuntimeInstance instance) {
        result = null;
        instance.acceptInstanceVisitor(this);
        copyMap.put(instance, result);
        if (parentStack.size() != 0) {
            throw new IllegalStateException();
        }
        return result;
    }

    private void visitChildren(BPMNRuntimeInstance original,
                               BPMNRuntimeInstance copy) {
        parentStack.addLast(copy);
        for (BPMNRuntimeInstance childInstance : original.getChildInstanceList()) {
            childInstance.acceptInstanceVisitor(this);
            copyMap.put(childInstance, result);
            copy.getChildInstanceList().add(result);
        }
        parentStack.removeLast();
        result = copy;
    }

    private void copyTokens(FlowElementsContainerInstance original,
                            FlowElementsContainerInstance copy) {
        copy.getTokenSet().addAll(original.getTokenSet());
    }

    @Override
    public void visitChoreographyInstance(ChoreographyInstance instance) {
        ChoreographyInstance instanceCopy = new ChoreographyInstanceImpl(
                getParent(),
                instance.getBaseElement()
        );
        copyTokens(instance, instanceCopy);
        visitChildren(instance, instanceCopy);
    }

    @Override
    public void visitCollaborationInstance(CollaborationInstance instance) {
        CollaborationInstance instanceCopy = new CollaborationInstanceImpl(
                getParent(),
                instance.getBaseElement()
        );
        visitChildren(instance, instanceCopy);
    }

    @Override
    public void visitProcessInstance(ProcessInstance instance) {
        ProcessInstance instanceCopy = new ProcessInstanceImpl(
                getParent(),
                instance.getBaseElement()
        );
        copyTokens(instance, instanceCopy);
        visitChildren(instance, instanceCopy);
    }

    @Override
    public void visitSubProcessInstance(SubProcessInstance instance) {
        SubProcessInstance instanceCopy = new SubProcessInstanceImpl(
                getActivityParent(),
                instance.getBaseElement(),
                instance.getActivityState()
        );
        copyTokens(instance, instanceCopy);
        visitChildren(instance, instanceCopy);
    }

    @Override
    public void visitTaskInstance(TaskInstance instance) {
        TaskInstance instanceCopy = new TaskInstanceImpl(
                getActivityParent(),
                instance.getBaseElement(),
                instance.getActivityState()
        );
        visitChildren(instance, instanceCopy);
    }

}
