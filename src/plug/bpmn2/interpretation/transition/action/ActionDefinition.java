package plug.bpmn2.interpretation.transition.action;

public interface ActionDefinition {

    void accept(Visitor visitor);

    interface Visitor {
        default void visitChangeState(ActivityAction.ChangeState action) {};
        default void visitAddToken(FlowAction.AddToken action) {};
        default void visitRemoveToken(FlowAction.RemoveToken action) {};
        default void visitOpenInstance(InstanceAction.Open action) {};
        default void visitCloseInstance(InstanceAction.Close action) {};
        default void visitSendMessage(MessageAction.Send action) {};
        default void visitReceiveMessage(MessageAction.Receive action) {};
    }

}
