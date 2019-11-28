package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.SubProcess;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.SubProcessInstance;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.model.instance.data.Token;

import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubProcessInstanceImpl that = (SubProcessInstanceImpl) o;
        return getTokenSet().equals(that.getTokenSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTokenSet());
    }

}
