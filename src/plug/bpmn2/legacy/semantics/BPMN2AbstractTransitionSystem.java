package plug.bpmn2.legacy.semantics;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.legacy.semantics.transition.BPMN2AbstractTransition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2AbstractTransitionSystem {

    private final Set<EObject> initiatorSet;
    private final Set<BPMN2AbstractTransition> transitionSet;

    public BPMN2AbstractTransitionSystem() {
        initiatorSet = new HashSet<>();
        transitionSet = new HashSet<>();
    }

    public Set<EObject> getInitiatorSet() {
        return initiatorSet;
    }

    public Set<BPMN2AbstractTransition> getTransitionSet() {
        return transitionSet;
    }

}
