package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.MessageFlow;

public interface MessageAction extends ActionDefinition {

    MessageFlow getMessageFlow();

    interface Send extends MessageAction {

        @Override
        default void accept(Visitor visitor) {
            visitor.visitSendMessage(this);
        }

    }

    interface Receive extends MessageAction {

        @Override
        default void accept(Visitor visitor) {
            visitor.visitReceiveMessage(this);
        }

    }
}
