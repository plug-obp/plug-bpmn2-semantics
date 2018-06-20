package plug.bpmn2.tools;

import plug.bpmn2.examples.simpleProcessInterpreter.SPIAbstractTransition;
import plug.bpmn2.examples.simpleProcessInterpreter.SPISystemConfiguration;
import plug.bpmn2.examples.simpleProcessInterpreter.SPITransitionFunction;
import plug.core.IFiredTransition;
import plug.core.ITransitionRelation;
import plug.statespace.transitions.FiredTransition;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2TransitionRelation implements ITransitionRelation<SPISystemConfiguration, SPIAbstractTransition> {

    private final SPITransitionFunction model;

    public BPMN2TransitionRelation(SPITransitionFunction model) {
        this.model = model;
    }


    @Override
    public Set<SPISystemConfiguration> initialConfigurations() {
        return model.getInitialConfigurations();
    }

    @Override
    public Collection<SPIAbstractTransition> fireableTransitionsFrom(SPISystemConfiguration spiSystemConfiguration) {
        return model.getTransitionsFrom(spiSystemConfiguration);
    }

    @Override
    public IFiredTransition<SPISystemConfiguration, ?> fireOneTransition(
            SPISystemConfiguration spiSystemConfiguration,
            SPIAbstractTransition spiAbstractTransition) {
        SPISystemConfiguration target = spiAbstractTransition.executeAction(spiSystemConfiguration);
        return new FiredTransition<>(spiSystemConfiguration, target, spiAbstractTransition);
    }

}
