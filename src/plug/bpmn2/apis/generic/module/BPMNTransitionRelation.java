package plug.bpmn2.apis.generic.module;

import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.transition.BPMNRuntimeTransition;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.core.IFiredTransition;
import plug.core.ITransitionRelation;
import plug.statespace.transitions.FiredTransition;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class BPMNTransitionRelation
        implements ITransitionRelation<BPMNRuntimeState, BPMNRuntimeTransition> {

    private final BPMNModelHandler modelHandler;

    public BPMNTransitionRelation(BPMNModelHandler modelHandler) {
            this.modelHandler = modelHandler;
    }


    @Override
    public Set<BPMNRuntimeState> initialConfigurations() {
        return Collections.singleton(new BPMNRuntimeState());
    }

    @Override
    public Collection<BPMNRuntimeTransition> fireableTransitionsFrom(BPMNRuntimeState state) {
        return Collections.EMPTY_SET;
    }

    @Override
    public IFiredTransition<BPMNRuntimeState, ?> fireOneTransition(
        BPMNRuntimeState state,
        BPMNRuntimeTransition transition
    ) {
        BPMNRuntimeState target = new BPMNRuntimeState();
        return new FiredTransition<>(state, target, transition);
    }

}
