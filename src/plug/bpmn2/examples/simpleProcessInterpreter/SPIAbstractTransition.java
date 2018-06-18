package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.tools.BPMN2PrinterShort;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public interface SPIAbstractTransition {

    // Meta information
    List<EObject> getSourceList();
    List<EObject> getMediumList();
    List<EObject> getTargetList();

    // Execution
    boolean evaluateGuard(SPISystemConfiguration source);
    SPISystemConfiguration executeAction(SPISystemConfiguration source);

}
