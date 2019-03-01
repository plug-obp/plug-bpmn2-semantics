package plug.bpmn2.interpretation.transition.action;

import plug.bpmn2.interpretation.model.instance.ActivityState;

public interface ChangeActivityStateAction extends ActivityAction {

    ActivityState targetActivityState();

}
