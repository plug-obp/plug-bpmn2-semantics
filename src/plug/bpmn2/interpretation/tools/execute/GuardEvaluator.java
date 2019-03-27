package plug.bpmn2.interpretation.tools.execute;

import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.transition.action.ActionDefinition;

public class GuardEvaluator {

    public boolean eval(
            BPMNModelRuntimeState state,
            BPMNRuntimeInstance scope,
            ActionDefinition action
    ) {
        // TODO
        return true;
    }

}
