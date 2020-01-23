package plug.bpmn2.dsim.tools;

import plug.bpmn2.dsim.tools.transitions.TransitionEndEvent;
import plug.bpmn2.dsim.tools.transitions.TransitionGateway;
import plug.bpmn2.dsim.tools.utils.TransitionsFireable;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.core.IFiredTransition;
import plug.statespace.transitions.FiredTransition;

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
    public IFiredTransition<BPMNRuntimeState, ?> fireOneTransition(BPMNRuntimeState state, Transition transition) {
        BPMNRuntimeState target = transition.execute(state);
        BPMNRuntimeState nextTarget = foldOne(target);
        while (nextTarget != null) {
            target = nextTarget;
            nextTarget = foldOne(target);
        }
        return new FiredTransition<>(state, target, transition);
    }
}
