package plug.bpmn2.interpretation.transition.action.definition;

import org.eclipse.bpmn2.MessageFlow;

public interface MessageAction extends ActionDefinition {

    @Override
    MessageFlow getBaseElementScope();

    interface Send extends MessageAction {

    }

    interface Receive extends MessageAction {

    }
}
