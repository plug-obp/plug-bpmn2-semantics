package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.interpretation.model.instance.ActivityInstance;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActivityInstanceBase<?> that = (ActivityInstanceBase<?>) o;
        return state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), state);
    }
}
