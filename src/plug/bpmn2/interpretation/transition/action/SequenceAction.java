package plug.bpmn2.interpretation.transition.action;

import java.util.List;

public interface SequenceAction extends BPMNRuntimeAction {

    List<BPMNRuntimeAction> actionList();

}
