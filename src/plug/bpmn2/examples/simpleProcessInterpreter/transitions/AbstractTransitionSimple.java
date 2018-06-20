package plug.bpmn2.examples.simpleProcessInterpreter.transitions;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.examples.simpleProcessInterpreter.SPISystemConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class AbstractTransitionSimple extends AbstractTransitionBase {

    @Override
    public boolean evaluateGuard(SPISystemConfiguration source) {
        List<EObject> availableTokens = new ArrayList<>();
        availableTokens.addAll(source.getTokens());
        for (EObject token : getIncommingList()) {
            if (!availableTokens.remove(token)) return false;
        }
        return true;
    }

    @Override
    public SPISystemConfiguration executeAction(SPISystemConfiguration source) {
        List<EObject> availableTokens = new ArrayList<>();
        availableTokens.addAll(source.getTokens());
        for (EObject token : getIncommingList()) {
            if (!availableTokens.remove(token)) return null;
        }
        availableTokens.addAll(getOutgoingList());
        SPISystemConfiguration result = new SPISystemConfiguration(availableTokens);
        result.canonize();
        return result;
    }

}
