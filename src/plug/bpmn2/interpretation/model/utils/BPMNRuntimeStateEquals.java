package plug.bpmn2.interpretation.model.utils;

import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;

import java.util.LinkedList;
import java.util.List;

public class BPMNRuntimeStateEquals implements BPMNInstanceVisitor {

    public boolean childrenEquals(List<BPMNRuntimeInstance> list1, List<BPMNRuntimeInstance> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        List<BPMNRuntimeInstance> remaining = new LinkedList<>(list2);
        for (BPMNRuntimeInstance instance1 : list1) {
            if (remaining.isEmpty()) {
                return false;
            }
            boolean foundMatch = false;
            iterateList2:
            for (BPMNRuntimeInstance instance2 : remaining) {
                if (instanceEquals(instance1, instance2)) {
                    foundMatch = true;
                    remaining.remove(instance2);
                    break iterateList2;
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        return remaining.isEmpty();
    }

    public boolean instanceEquals(BPMNRuntimeInstance instance1, BPMNRuntimeInstance instance2) {
        otherInstance = instance2;
        instance1.acceptInstanceVisitor(this);
        return result;
    }

    public boolean stateEquals(BPMNRuntimeState state1, BPMNRuntimeState state2) {
        if (state1.getEventFlowDataList().size() != state2.getEventFlowDataList().size() ||
                state1.getMessageFlowDataList().size() != state2.getMessageFlowDataList().size() ||
                state1.getIntermediateFlagList().size() != state2.getIntermediateFlagList().size()) {
            return false;
        }
        if (!state1.getIntermediateFlagList().equals(state2.getIntermediateFlagList())) {
            return false;
        }
        if (!childrenEquals(state1.getRootInstanceList(), state2.getRootInstanceList())) {
            return false;
        }
        return true;
    }

    private BPMNRuntimeInstance otherInstance;
    private boolean result;

    @Override
    public void visitChoreographyInstance(ChoreographyInstance instance) {
        if (!(otherInstance instanceof ChoreographyInstance)) {
            result = false;
            return;
        }
        ChoreographyInstance otherChoreography = (ChoreographyInstance) otherInstance;
        if (!instance.getTokenSet().equals(otherChoreography.getTokenSet())) {
            result = false;
            return;
        }
        result = childrenEquals(
                instance.getChildInstanceList(),
                otherChoreography.getChildInstanceList()
        );
    }

    @Override
    public void visitCollaborationInstance(CollaborationInstance instance) {
        if (!(otherInstance instanceof CollaborationInstance)) {
            result = false;
            return;
        }
        CollaborationInstance otherCollaboration = (CollaborationInstance) otherInstance;
        result = childrenEquals(
                instance.getChildInstanceList(),
                otherCollaboration.getChildInstanceList()
        );
    }

    @Override
    public void visitProcessInstance(ProcessInstance instance) {
        if (!(otherInstance instanceof ProcessInstance)) {
            result = false;
            return;
        }
        ProcessInstance otherProcess = (ProcessInstance) otherInstance;
        if (!instance.getTokenSet().equals(otherProcess.getTokenSet())) {
            result = false;
            return;
        }
        result = childrenEquals(
                instance.getChildInstanceList(),
                otherProcess.getChildInstanceList()
        );
    }

    @Override
    public void visitSubProcessInstance(SubProcessInstance instance) {
        if (!(otherInstance instanceof SubProcessInstance)) {
            result = false;
            return;
        }
        SubProcessInstance otherSubProcess = (SubProcessInstance) otherInstance;
        if (!instance.getTokenSet().equals(otherSubProcess.getTokenSet())) {
            result = false;
            return;
        }
        if (!instance.getActivityState().equals(otherSubProcess.getActivityState())) {
            result = false;
            return;
        }
        result = childrenEquals(
                instance.getChildInstanceList(),
                otherSubProcess.getChildInstanceList()
        );
    }

    @Override
    public void visitTaskInstance(TaskInstance instance) {
        if (!(otherInstance instanceof TaskInstance)) {
            result = false;
            return;
        }
        TaskInstance otherTask = (TaskInstance) otherInstance;
        if (!instance.getActivityState().equals(otherTask.getActivityState())) {
            result = false;
            return;
        }
        result = childrenEquals(
                instance.getChildInstanceList(),
                otherTask.getChildInstanceList()
        );
    }

}
