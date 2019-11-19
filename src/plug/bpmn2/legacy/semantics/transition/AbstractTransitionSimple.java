package plug.bpmn2.legacy.semantics.transition;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.legacy.semantics.BPMN2SystemConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class AbstractTransitionSimple extends AbstractTransitionBase {

    @Override
    public boolean evaluateGuard(BPMN2SystemConfiguration source) {
        List<EObject> availableTokens = new ArrayList<>();
        availableTokens.addAll(source.getTokens());
        for (EObject token : getIncomingList()) {
            if (!availableTokens.remove(token)) return false;
        }
        return true;
    }

    @Override
    public BPMN2SystemConfiguration executeAction(BPMN2SystemConfiguration source) {
        List<EObject> availableTokens = new ArrayList<>();
        availableTokens.addAll(source.getTokens());
        for (EObject token : getIncomingList()) {
            if (!availableTokens.remove(token)) return null;
        }
        availableTokens.addAll(getOutgoingList());
        BPMN2SystemConfiguration result = new BPMN2SystemConfiguration(availableTokens);
        result.canonize();
        return result;
    }

}
