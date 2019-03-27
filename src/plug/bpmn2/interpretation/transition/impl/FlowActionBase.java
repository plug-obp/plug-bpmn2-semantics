package plug.bpmn2.interpretation.transition.impl;

import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.interpretation.transition.action.FlowAction;

public abstract class FlowActionBase implements FlowAction {

    private final Token token;

    public FlowActionBase(Token token) {
        this.token = token;
    }

    @Override
    public Token getToken() {
        return null;
    }

}
