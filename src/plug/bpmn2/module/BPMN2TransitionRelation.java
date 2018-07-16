package plug.bpmn2.module;

import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;
import plug.bpmn2.semantics.BPMN2SystemConfiguration;
import plug.bpmn2.semantics.BPMN2TransitionFunction;
import plug.core.IFiredTransition;
import plug.core.ITransitionRelation;
import plug.statespace.transitions.FiredTransition;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2TransitionRelation implements ITransitionRelation<BPMN2SystemConfiguration, BPMN2AbstractTransition> {

    private final BPMN2TransitionFunction model;

    public BPMN2TransitionRelation(BPMN2TransitionFunction model) {
        this.model = model;
    }


    @Override
    public Set<BPMN2SystemConfiguration> initialConfigurations() {
        return model.getInitialConfigurations();
    }

    @Override
    public Collection<BPMN2AbstractTransition> fireableTransitionsFrom(BPMN2SystemConfiguration bpmn2SystemConfiguration) {
        return model.getTransitionsFrom(bpmn2SystemConfiguration);
    }

    @Override
    public IFiredTransition<BPMN2SystemConfiguration, ?> fireOneTransition(
            BPMN2SystemConfiguration bpmn2SystemConfiguration,
            BPMN2AbstractTransition bpmn2AbstractTransition) {
        BPMN2SystemConfiguration target = bpmn2AbstractTransition.executeAction(bpmn2SystemConfiguration);
        return new FiredTransition<>(bpmn2SystemConfiguration, target, bpmn2AbstractTransition);
    }

}
