package plug.bpmn2.semantics.state.instance.impl;

import org.eclipse.bpmn2.SubProcess;
import plug.bpmn2.semantics.state.BPMNInstanceVisitor;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.state.instance.SubProcessInstance;
import plug.bpmn2.semantics.state.instance.data.ActivityState;
import plug.bpmn2.semantics.state.instance.data.Token;

import java.util.HashSet;
import java.util.Set;

public class SubProcessInstanceImpl
        extends ActivityInstanceBase<SubProcess>
        implements SubProcessInstance {

    private final Set<Token> tokenSet;

    public SubProcessInstanceImpl(
            FlowElementsContainerInstance parent,
            SubProcess baseElement,
            ActivityState state) {
        super(parent, baseElement, state);
        tokenSet = new HashSet<>();
    }

    @Override
    public Set<Token> getTokenSet() {
        return tokenSet;
    }

    @Override
    public void acceptInstanceVisitor(BPMNInstanceVisitor visitor) {
        visitor.visitSubProcessInstance(this);
    }

}
