package plug.bpmn2.legacy.semantics.transition;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.legacy.semantics.BPMN2SystemConfiguration;

import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public interface BPMN2AbstractTransition {

    // Meta information
    List<EObject> getIncomingList();
    List<EObject> getNodeList();
    List<EObject> getOutgoingList();

    // Execution
    boolean evaluateGuard(BPMN2SystemConfiguration source);
    BPMN2SystemConfiguration executeAction(BPMN2SystemConfiguration source);

}
