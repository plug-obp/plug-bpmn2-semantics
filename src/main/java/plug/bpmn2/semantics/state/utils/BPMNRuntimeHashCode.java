package plug.bpmn2.semantics.state.utils;

import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.ActivityInstance;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;

import java.util.List;

public class BPMNRuntimeHashCode {

    private BPMNRuntimeHashCode() {}

    static private int hashChildren(List<?> children) {
        int result = 0;
        for (Object child : children) {
            result += child.hashCode();
        }
        return result;
    }

    static public int hashState(BPMNRuntimeState state) {
        return hashChildren(state.getRootInstanceList());
    }

    static public int hashInstance(BPMNRuntimeInstance instance) {
        int result = hashChildren(instance.getChildInstanceList());
        result += instance.getBaseElement().hashCode();
        if (instance instanceof FlowElementsContainerInstance) {
            FlowElementsContainerInstance flowElementsContainerInstance = (FlowElementsContainerInstance) instance;
            result += flowElementsContainerInstance.getTokenSet().hashCode();
        }
        if (instance instanceof ActivityInstance) {
            ActivityInstance activity = (ActivityInstance) instance;
            result += activity.getActivityState().hashCode();
        }
        return result;
    }

}
