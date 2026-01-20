package plug.bpmn2.semantics.transition.kinds;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.transition.Transition;
import plug.bpmn2.semantics.transition.utils.ElementsOpen;

import java.util.Objects;

public class TransitionOpen extends Transition {

    private final BaseElement baseElement;

    public TransitionOpen(BPMNModelHandler model, BPMNRuntimeState state, BPMNRuntimeInstance scope, BaseElement baseElement) {
        super(model, state, scope);
        this.baseElement = baseElement;
    }

    @Override
    public BaseElement getBaseElement() {
        return baseElement;
    }

    @Override
    public boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target) {
        boolean execute = target != null;
        BPMNRuntimeState state = execute ? target : source;
        BPMNRuntimeInstance scope = getScope(state);
        if (!(getBaseElement() instanceof Activity)) return false;
        return ElementsOpen.open(getModel(), state, scope, getBaseElement(), execute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransitionOpen open = (TransitionOpen) o;
        return getBaseElement().equals(open.getBaseElement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBaseElement());
    }

    @Override
    public String toString() {
        return "Open " + getModel().printer.getShortString(getBaseElement());
    }

}
