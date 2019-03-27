package plug.bpmn2.semantics.transition;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.tools.analysis.resource.BPMN2PrinterShort;

import java.util.*;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public abstract class AbstractTransitionBase implements BPMN2AbstractTransition {

    private final List<EObject> incomingList;
    private final List<EObject> nodeList;
    private final List<EObject> outgoingList;

    public AbstractTransitionBase() {
        incomingList = new ArrayList<>();
        nodeList = new ArrayList<>();
        outgoingList = new ArrayList<>();
    }

    @Override
    public List<EObject> getIncomingList() {
        return incomingList;
    }

    @Override
    public List<EObject> getNodeList() {
        return nodeList;
    }

    @Override
    public List<EObject> getOutgoingList() {
        return outgoingList;
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
        appendCollection(result, incomingList);
        result.append(" -- ");
        appendCollection(result, nodeList);
        result.append(" -> ");
        appendCollection(result, outgoingList);
        return result.toString();
    }

}
