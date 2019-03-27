package plug.bpmn2.interpretation.transition.impl;

import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.interpretation.transition.action.FlowAction;

public class AddTokenImpl extends FlowActionBase implements FlowAction.AddToken {

    public AddTokenImpl(Token token) {
        super(token);
    }

}
