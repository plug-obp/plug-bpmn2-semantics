package plug.bpmn2.semantics.transition;

import obp2.runtime.core.ITransitionRelation;
import obp2.runtime.core.defaults.DefaultLanguageService;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.transition.utils.TransitionsFireable;
import plug.bpmn2.semantics.transition.utils.TransitionsInitial;

import java.util.Collection;
import java.util.Set;

public class TransitionRelation
        extends DefaultLanguageService<BPMNRuntimeState, Transition, Void>
        implements ITransitionRelation<BPMNRuntimeState, Transition, Void> {

    private final BPMNModelHandler model;

    public TransitionRelation(BPMNModelHandler model) {
        this.model = model;
    }

    public BPMNModelHandler getModel() {
		return model;
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
    public BPMNFiredTransition fireOneTransition(BPMNRuntimeState state, Transition transition) {
        BPMNRuntimeState target = transition.execute(state);
        return new BPMNFiredTransition(state, target, transition);
    }

}
