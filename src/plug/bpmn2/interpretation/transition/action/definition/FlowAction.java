package plug.bpmn2.interpretation.transition.action.definition;

import org.eclipse.bpmn2.FlowElementsContainer;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.interpretation.transition.action.definition.ActionDefinition;

public interface FlowAction extends ActionDefinition {

    @Override
    FlowElementsContainer getBaseElementScope();

    Token getToken();

    interface AddToken extends FlowAction {

    }

    interface RemoveToken extends FlowAction {

    }

}
