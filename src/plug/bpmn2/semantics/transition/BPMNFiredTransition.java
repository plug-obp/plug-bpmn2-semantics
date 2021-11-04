package plug.bpmn2.semantics.transition;

import obp2.core.defaults.FiredTransition;
import plug.bpmn2.semantics.state.BPMNRuntimeState;

public class BPMNFiredTransition extends FiredTransition<BPMNRuntimeState, Transition, Void> {

    public BPMNFiredTransition(BPMNRuntimeState source, BPMNRuntimeState target, Transition fired) {
        super(source, target, fired);
    }

}
