package plug.bpmn2.interpretation.transition.impl;

import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.transition.action.ActivityAction;

public class ChangeStateImpl implements ActivityAction.ChangeState {

    private final ActivityState activityState;

    public ChangeStateImpl(ActivityState activityState) {
        this.activityState = activityState;
    }

    @Override
    public ActivityState targetActivityState() {
        return null;
    }

}
