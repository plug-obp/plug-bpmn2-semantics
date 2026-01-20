package plug.bpmn2.semantics.state;

import plug.bpmn2.semantics.state.instance.*;

public interface BPMNInstanceVisitor {
    default void visitChoreographyInstance(ChoreographyInstance instance) {}
    default void visitCollaborationInstance(CollaborationInstance instance) {}
    default void visitProcessInstance(ProcessInstance instance) {}
    default void visitSubProcessInstance(SubProcessInstance instance) {}
    default void visitTaskInstance(TaskInstance instance) {}
}
