package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Process;
import plug.bpmn2.interpretation.model.instance.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeToken;
import plug.bpmn2.interpretation.model.instance.InstanceVisitor;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;

import java.util.HashSet;
import java.util.Set;

public class ProcessInstanceImpl
        extends InstanceBase<BPMNRuntimeInstance, Process>
        implements ProcessInstance {

    private final Set<BPMNRuntimeToken> tokenSet;

    public ProcessInstanceImpl(BPMNRuntimeInstance parent, Process baseElement) {
        super(parent, baseElement);
        tokenSet = new HashSet<>();
    }

    @Override
    public Set<BPMNRuntimeToken> tokenSet() {
        return tokenSet;
    }

    @Override
    public void acceptInstanceVisitor(InstanceVisitor visitor) {
        visitor.visitProcessInstance(this);
    }

}
