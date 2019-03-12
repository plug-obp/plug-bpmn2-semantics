package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;

public interface ActivityInstance extends BPMNRuntimeInstance {

    @Override
    FlowElementsContainerInstance getParent();

    @Override
    Activity getBaseElement();

    ActivityState getActivityState();

}
