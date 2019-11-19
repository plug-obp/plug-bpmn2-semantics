package plug.bpmn2.tools.runtime.system;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;

@FunctionalInterface
public interface TransitionGuard {

    TransitionGuard TRUE = (s, rs) -> true;

    boolean check(BPMNRuntimeState state, BPMNRuntimeInstance runtimeScope);

}
