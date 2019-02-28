package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.SubProcess;
import plug.bpmn2.interpretation.model.BPMNRuntimeToken;
import plug.bpmn2.interpretation.model.instance.ActivityState;
import plug.bpmn2.interpretation.model.instance.InstanceVisitor;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.SubProcessInstance;

import java.util.HashSet;
import java.util.Set;

public class SubProcessInstanceImpl
        extends ActivityInstanceBase<SubProcess>
        implements SubProcessInstance {

    private final Set<BPMNRuntimeToken> tokenSet;

    public SubProcessInstanceImpl(
            FlowElementsContainerInstance parent,
            SubProcess baseElement,
            ActivityState state) {
        super(parent, baseElement, state);
        tokenSet = new HashSet<>();
    }

    @Override
    public Set<BPMNRuntimeToken> tokenSet() {
        return tokenSet;
    }

    @Override
    public void acceptInstanceVisitor(InstanceVisitor visitor) {
        visitor.visitSubProcessInstance(this);
    }

}
