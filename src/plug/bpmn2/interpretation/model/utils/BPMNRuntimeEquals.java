package plug.bpmn2.interpretation.model.utils;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;

import java.util.LinkedList;
import java.util.List;

public class BPMNRuntimeEquals {

    static public <T> boolean childrenEquals(List<T> list1,
                                            List<T> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        List<T> remaining = new LinkedList<>(list2);
        for (T element1 : list1) {
            if (remaining.isEmpty()) {
                throw new IllegalStateException("Testing equals, remaining should never be empty before the end");
            }
            boolean foundMatch = false;
            iterateList2:
            for (T element2 : remaining) {
                if (element1.equals(element2)) {
                    foundMatch = true;
                    remaining.remove(element2);
                    break iterateList2;
                }
            }
            if (!foundMatch) {
                return false;
            }
        }
        if (!remaining.isEmpty()) {
            throw new IllegalStateException("Testing equals, remaining should always end empty");
        }
        return true;
    }

    static private <T extends BPMNRuntimeInstance> boolean instanceEquals(T instance1,
                                                                          BPMNRuntimeInstance instance2,
                                                                          Class<T> clazz) {
        if (!clazz.isInstance(instance2)) {
            return false;
        }
        if (!instance1.getBaseElement().equals(instance2.getBaseElement())) {
            return false;
        }
        if (instance1 instanceof ActivityInstance) {
            ActivityInstance activity1 = (ActivityInstance) instance1;
            ActivityInstance activity2 = (ActivityInstance) instance2;
            if (!activity1.getActivityState().equals(activity2.getActivityState())) {
                return false;
            }
        }
        if (instance1 instanceof FlowElementsContainerInstance) {
            FlowElementsContainerInstance flowElementsContainerInstance1 = (FlowElementsContainerInstance) instance1;
            FlowElementsContainerInstance flowElementsContainerInstance2 = (FlowElementsContainerInstance) instance2;
            if (!flowElementsContainerInstance1.getTokenSet().equals(flowElementsContainerInstance2.getTokenSet())) {
                return false;
            }
        }
        return childrenEquals(
                instance1.getChildInstanceList(),
                instance2.getChildInstanceList()
        );
    }

    static public boolean instanceEquals(BPMNRuntimeInstance instance1,
                                         BPMNRuntimeInstance instance2) {
        if (instance1 instanceof SubProcessInstance) {
            return instanceEquals(
                    (SubProcessInstance) instance1,
                    instance2,
                    SubProcessInstance.class
            );
        } else if (instance1 instanceof ChoreographyInstance) {
            return instanceEquals(
                    (ChoreographyInstance) instance1,
                    instance2,
                    ChoreographyInstance.class
            );
        } else if (instance1 instanceof ProcessInstance) {
            return instanceEquals(
                    (ProcessInstance) instance1,
                    instance2,
                    ProcessInstance.class
            );
        } else if (instance1 instanceof TaskInstance) {
            return instanceEquals(
                    (TaskInstance) instance1,
                    instance2,
                    TaskInstance.class
            );
        } else if (instance1 instanceof CollaborationInstance) {
            return instanceEquals(
                    (CollaborationInstance) instance1,
                    instance2,
                    CollaborationInstance.class
            );
        }
        throw new IllegalArgumentException("Unsupported instance for equals");
    }

    public static boolean stateEquals(BPMNRuntimeState state1, BPMNRuntimeState state2) {
        // TODO check other fields
        return childrenEquals(
                state1.getRootInstanceList(),
                state2.getRootInstanceList()
        );
    }

}
