package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.emf.ecore.EObject;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPIAbstractTransitionSystem {

    private final Set<EObject> initiatorSet;
    private final Set<SPIAbstractTransition> transitionSet;

    public SPIAbstractTransitionSystem() {
        initiatorSet = new HashSet<>();
        transitionSet = new HashSet<>();
    }

    public Set<EObject> getInitiatorSet() {
        return initiatorSet;
    }

    public Set<SPIAbstractTransition> getTransitionSet() {
        return transitionSet;
    }

}
