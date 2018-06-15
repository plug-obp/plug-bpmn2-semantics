package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.tools.BPMN2PrinterShort;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public abstract class SPIAbstractTransition {

    static public SPIAbstractTransition base(
            Predicate<SPISystemConfiguration> guard,
            Function<SPISystemConfiguration, SPISystemConfiguration> action) {
        return new SPIAbstractTransition() {
            @Override
            boolean evaluateGuard(SPISystemConfiguration source) {
                return guard.test(source);
            }

            @Override
            SPISystemConfiguration executeAction(SPISystemConfiguration source) {
                return action.apply(source);
            }
        };
    }

    private final Set<EObject> initiatorSet;

    public SPIAbstractTransition() {
        initiatorSet = new HashSet<>();
    }

    Set<EObject> getInitiatorSet() {
        return initiatorSet;
    }

    abstract boolean evaluateGuard(SPISystemConfiguration source);

    abstract SPISystemConfiguration executeAction(SPISystemConfiguration source);

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Initiators [");
        for (EObject initiator : getInitiatorSet()) {
            result.append(" " + BPMN2PrinterShort.INSTANCE.getShortString(initiator) + " ");
        }
        result.append("]");
        return result.toString();
    }
}
