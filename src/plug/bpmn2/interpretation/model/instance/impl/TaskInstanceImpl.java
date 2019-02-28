package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Task;
import plug.bpmn2.interpretation.model.instance.ActivityState;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.InstanceVisitor;
import plug.bpmn2.interpretation.model.instance.TaskInstance;

public class TaskInstanceImpl extends ActivityInstanceBase<Task> implements TaskInstance {

    public TaskInstanceImpl(FlowElementsContainerInstance parent, Task baseElement, ActivityState state) {
        super(parent, baseElement, state);
    }

    @Override
    public void acceptInstanceVisitor(InstanceVisitor visitor) {
        visitor.visitTaskInstance(this);
    }

}
