package plug.bpmn2.semantics;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.tools.analysis.resource.BPMN2PrinterShort;
import plug.core.defaults.DefaultConfiguration;

import java.util.*;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2SystemConfiguration extends DefaultConfiguration<BPMN2SystemConfiguration> {

    private final List<EObject> tokens;

    public BPMN2SystemConfiguration(List<EObject> tokens) {
        this.tokens = new ArrayList<>();
        this.tokens.addAll(tokens);
    }

    public List<EObject> getTokens() {
        return tokens;
    }

    public void canonize() {
        tokens.sort(Comparator.comparingInt(Object::hashCode));
    }

    @Override
    public BPMN2SystemConfiguration createCopy() {
        BPMN2SystemConfiguration result =  new BPMN2SystemConfiguration(new ArrayList<>());
        result.getTokens().addAll(this.getTokens());
        return result;
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BPMN2SystemConfiguration that = (BPMN2SystemConfiguration) o;
        return Objects.equals(getTokens(), that.getTokens());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Tokens [");
        for (EObject token : getTokens()) {
            result.append(" " + BPMN2PrinterShort.INSTANCE.getShortString(token) + " ");
        }
        result.append("]");
        return result.toString();
    }

}
