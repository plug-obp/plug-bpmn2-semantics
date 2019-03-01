package plug.bpmn2.interpretation.model.instance;

public interface InstanceVisitor {
    default void visitChoreographyInstance(ChoreographyInstance instance) {}
    default void visitCollaborationInstance(CollaborationInstance instance) {}
    default void visitProcessInstance(ProcessInstance instance) {}
    default void visitSubProcessInstance(SubProcessInstance instance) {}
    default void visitTaskInstance(TaskInstance instance) {}
}
