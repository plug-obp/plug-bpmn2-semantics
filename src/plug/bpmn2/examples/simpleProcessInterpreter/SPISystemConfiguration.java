package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.tools.BPMN2PrinterShort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPISystemConfiguration {

    private final List<EObject> tokens;

    public SPISystemConfiguration(List<EObject> tokens) {
        this.tokens = new ArrayList<>();
        this.tokens.addAll(tokens);
    }

    public List<EObject> getTokens() {
        return tokens;
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SPISystemConfiguration that = (SPISystemConfiguration) o;
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
