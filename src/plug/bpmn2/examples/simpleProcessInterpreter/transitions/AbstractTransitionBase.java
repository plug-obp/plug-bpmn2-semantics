package plug.bpmn2.examples.simpleProcessInterpreter.transitions;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.examples.simpleProcessInterpreter.SPIAbstractTransition;
import plug.bpmn2.tools.BPMN2PrinterShort;

import java.util.*;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public abstract class AbstractTransitionBase implements SPIAbstractTransition {

    private final List<EObject> sourceList;
    private final List<EObject> mediumList;
    private final List<EObject> targetList;

    public AbstractTransitionBase() {
        sourceList = new ArrayList<>();
        mediumList = new ArrayList<>();
        targetList = new ArrayList<>();
    }

    @Override
    public List<EObject> getSourceList() {
        return sourceList;
    }

    @Override
    public List<EObject> getMediumList() {
        return mediumList;
    }

    @Override
    public List<EObject> getTargetList() {
        return targetList;
    }

    static private void appendCollection(StringBuilder builder, Collection<EObject> elements) {
        builder.append("[");
        for (EObject element : elements) {
            builder.append(" " + BPMN2PrinterShort.INSTANCE.getShortString(element) + " ");
        }
        builder.append("]");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        appendCollection(result, sourceList);
        result.append(" -- ");
        appendCollection(result, mediumList);
        result.append(" -> ");
        appendCollection(result, targetList);
        return result.toString();
    }

}
