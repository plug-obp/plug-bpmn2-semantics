package plug.bpmn2.tools.sandbox.transition;

import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

@FunctionalInterface
public interface TransitionGuard {

    TransitionGuard TRUE = (s, rs) -> true;

    boolean check(BPMNModelRuntimeState state, BPMNRuntimeInstance runtimeScope);

}
