package plug.bpmn2.interpretation.transition.action;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.transition.action.definition.ActionDefinition;

public interface BPMNRuntimeAction {

    ActionDefinition getDefinition();
    BPMNRuntimeInstance getInstanceScope();

}
