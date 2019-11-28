package plug.bpmn2.dsim.tools;

import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.core.IFiredTransition;
import plug.core.ITransitionRelation;
import plug.statespace.transitions.FiredTransition;

import java.util.Collection;
import java.util.Set;

public class TransitionRelation
        implements ITransitionRelation<BPMNRuntimeState, Transition> {

    private final BPMNModelHandler model;

    public TransitionRelation(BPMNModelHandler model) {
        this.model = model;
    }

    @Override
    public Set<BPMNRuntimeState> initialConfigurations() {
        return TransitionsInitial.get(model);
    }

    @Override
    public Collection<Transition> fireableTransitionsFrom(BPMNRuntimeState state) {
        return TransitionsFireable.get(model, state);
    }

    @Override
    public IFiredTransition<BPMNRuntimeState, ?> fireOneTransition(BPMNRuntimeState state, Transition transition) {
        BPMNRuntimeState target = transition.execute(state);
        return new FiredTransition<>(state, target, transition);
    }

}
