package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Process;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;

import java.util.HashSet;
import java.util.Set;

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
