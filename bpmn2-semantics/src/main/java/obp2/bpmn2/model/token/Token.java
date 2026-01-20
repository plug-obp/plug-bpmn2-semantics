package obp2.bpmn2.model.token;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.FlowNode;

import java.util.List;
import java.util.Objects;

public class Token {

    private final List<CallActivity> callStack;
    private final BaseElement placeBaseElement;
    private final FlowNode actionFlowNode;
    private int id;

    public Token(List<CallActivity> callStack, BaseElement placeBaseElement) {
        this.callStack = callStack;
        this.placeBaseElement = placeBaseElement;
        actionFlowNode = TokenPoolUtils.getActionFlowNodeFrom(placeBaseElement);
    }

    public List<CallActivity> getCallStack() {
        return callStack;
    }

    public BaseElement getPlaceBaseElement() {
        return placeBaseElement;
    }

    public FlowNode getActionFlowNode() {
        return actionFlowNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return callStack.equals(token.callStack) && placeBaseElement.equals(token.placeBaseElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callStack, placeBaseElement);
    }

}
