package plug.bpmn2.interpretation.transition.action;

import java.util.List;

public interface ActionSequence extends ActionDefinition {

    List<ActionDefinition> getActionList();

    @Override
    default void accept(Visitor visitor) {
        for (ActionDefinition action : getActionList()) {
            action.accept(visitor);
        }
    }

}
