package plug.bpmn2.interpretation.transition;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

public interface BPMNRuntimeTransition {

    BPMNRuntimeInstance getRuntimeScope();
    BPMNAbstractTransition getActionSet();

}
