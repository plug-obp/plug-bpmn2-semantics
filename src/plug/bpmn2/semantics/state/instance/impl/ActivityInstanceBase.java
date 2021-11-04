package plug.bpmn2.semantics.state.instance.impl;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.semantics.state.instance.ActivityInstance;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.state.instance.data.ActivityState;

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
    public ActivityState getActivityState() {
        return state;
    }

}
