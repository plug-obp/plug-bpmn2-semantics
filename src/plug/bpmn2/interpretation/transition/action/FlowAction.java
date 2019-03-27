package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.FlowElementsContainer;
import plug.bpmn2.interpretation.model.instance.data.Token;

public interface FlowAction extends ActionDefinition {

    Token getToken();

    interface AddToken extends FlowAction {

        @Override
        default void accept(Visitor visitor) {
            visitor.visitAddToken(this);
        }
    }

    interface RemoveToken extends FlowAction {

        @Override
        default void accept(Visitor visitor) {
            visitor.visitRemoveToken(this);
        }

    }

}
