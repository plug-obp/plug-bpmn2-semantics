package plug.bpmn2.interpretation.transition.action.definition;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;

public interface ActivityAction extends ActionDefinition {

    @Override
    Activity getBaseElementScope();

    interface ChangeState extends ActivityAction {
        ActivityState targetActivityState();
    }

}
