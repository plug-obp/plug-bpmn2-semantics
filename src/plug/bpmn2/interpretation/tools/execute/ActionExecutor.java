package plug.bpmn2.interpretation.tools.execute;

import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.transition.action.ActionDefinition;

public class ActionExecutor {

    public BPMNModelRuntimeState execute(
            BPMNModelRuntimeState state,
            BPMNRuntimeInstance scope,
            ActionDefinition action
    ) {
        BPMNModelRuntimeState target = new BPMNModelRuntimeState();
        // TODO
        return target;
    }

}
