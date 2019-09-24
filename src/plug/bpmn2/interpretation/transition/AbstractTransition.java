package plug.bpmn2.interpretation.transition;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.transition.action.ActionDefinition;

import java.util.HashSet;
import java.util.Set;

public class AbstractTransition {

    private final BaseElement scope;
    private final Set<ActionDefinition> actionSet;

    public AbstractTransition(BaseElement scope) {
        this.scope = scope;
        actionSet = new HashSet<>();
    }

    public BaseElement getScope() {
        return scope;
    }

    public Set<ActionDefinition> getActionSet() {
        return actionSet;
    }

}
