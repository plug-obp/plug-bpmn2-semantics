package plug.bpmn2.semantics.transition;

import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.transition.kinds.TransitionEndEvent;
import plug.bpmn2.semantics.transition.kinds.TransitionGateway;
import plug.bpmn2.semantics.transition.utils.TransitionsFireable;

import java.util.Collection;

public class FoldTransitionRelation extends TransitionRelation {

    public FoldTransitionRelation(BPMNModelHandler model) {
        super(model);
    }

    private BPMNRuntimeState foldOne(BPMNRuntimeState source) {
        Collection<Transition> outgoingTransitions =  TransitionsFireable.get(getModel(), source);
        for (Transition transition : outgoingTransitions) {
            if (transition instanceof TransitionEndEvent || transition instanceof TransitionGateway) {
                return transition.execute(source);
            }
        }
        return null;
    }

    @Override
    public BPMNFiredTransition fireOneTransition(BPMNRuntimeState state, Transition transition) {
        BPMNRuntimeState target = transition.execute(state);
        BPMNRuntimeState nextTarget = foldOne(target);
        while (nextTarget != null) {
            target = nextTarget;
            nextTarget = foldOne(target);
        }
        return new BPMNFiredTransition(state, target, transition);
    }
}
