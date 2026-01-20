package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.instance.data.ActivityState;

public interface ActivityInstance extends BPMNRuntimeInstance {

    @Override
    FlowElementsContainerInstance getParent();

    @Override
    Activity getBaseElement();

    ActivityState getActivityState();

}
