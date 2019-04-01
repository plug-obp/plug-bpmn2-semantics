package plug.bpmn2.interpretation.transition.action;

import plug.bpmn2.interpretation.model.instance.data.ActivityState;

public interface ActivityAction extends ActionDefinition {

    interface ChangeState extends ActivityAction {

        ActivityState getTargetActivityState();

        @Override
        default void accept(Visitor visitor) {
            visitor.visitChangeState(this);
        }

    }

}
