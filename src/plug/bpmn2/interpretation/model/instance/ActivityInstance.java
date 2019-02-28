package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Activity;

public interface ActivityInstance extends BPMNRuntimeInstance {

    @Override
    FlowElementsContainerInstance parent();

    @Override
    Activity baseElement();

    ActivityState activityState();

}
