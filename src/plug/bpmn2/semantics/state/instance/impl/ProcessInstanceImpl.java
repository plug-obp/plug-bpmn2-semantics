package plug.bpmn2.semantics.state.instance.impl;

import org.eclipse.bpmn2.Process;
import plug.bpmn2.semantics.state.BPMNInstanceVisitor;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.instance.ProcessInstance;

public class ProcessInstanceImpl
        extends FlowElementContainerInstanceBase<Process>
        implements ProcessInstance {

    public ProcessInstanceImpl(BPMNRuntimeInstance parent, Process baseElement) {
        super(parent, baseElement);
    }

    @Override
    public void acceptInstanceVisitor(BPMNInstanceVisitor visitor) {
        visitor.visitProcessInstance(this);
    }

}
