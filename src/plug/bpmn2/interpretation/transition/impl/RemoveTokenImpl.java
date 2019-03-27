package plug.bpmn2.interpretation.transition.impl;

import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.interpretation.transition.action.FlowAction;

public class RemoveTokenImpl extends FlowActionBase implements FlowAction.RemoveToken {

    public RemoveTokenImpl(Token token) {
        super(token);
    }

}
