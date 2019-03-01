package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.interpretation.model.instance.ActivityState;
import plug.bpmn2.interpretation.model.instance.ActivityInstance;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;

abstract class ActivityInstanceBase<T extends Activity>
        extends InstanceBase<FlowElementsContainerInstance, T>
        implements ActivityInstance {

    private final ActivityState state;

    public ActivityInstanceBase(
            FlowElementsContainerInstance parent,
            T baseElement,
            ActivityState state) {
        super(parent, baseElement);
        this.state = state;
    }

    @Override
    public ActivityState activityState() {
        return state;
    }

}
