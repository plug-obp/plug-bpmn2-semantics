package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Objects;

public abstract class Transition {

    private final BPMNModelHandler model;
    private final RuntimeScope scope;

    public Transition(BPMNModelHandler model, BPMNRuntimeState state, BPMNRuntimeInstance scope) {
        this.model = model;
        this.scope = new RuntimeScope(state, scope);
    }

    public BPMNModelHandler getModel() {
        return model;
    }

    public RuntimeScope getScope() {
        return scope;
    }

    public BPMNRuntimeInstance getScope(BPMNRuntimeState state) {
        return scope.getInstance(state);
    }

    public abstract boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target);

    public boolean guard(BPMNRuntimeState source) {
        return attempt(source, null);
    }

    public BPMNRuntimeState execute(BPMNRuntimeState source) {
        BPMNRuntimeState target = ElementsCopy.copy(getModel(), source);
        attempt(source, target);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return getModel().equals(that.getModel()) &&
                Objects.equals(getScope(), that.getScope());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getScope());
    }

    public static class Open extends Transition {

        private final BaseElement baseElement;

        public Open(BPMNModelHandler model, BPMNRuntimeState state, BPMNRuntimeInstance scope, BaseElement baseElement) {
            super(model, state, scope);
            this.baseElement = baseElement;
        }

        public BaseElement getBaseElement() {
            return baseElement;
        }

        @Override
        public boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target) {
            boolean execute = target != null;
            BPMNRuntimeState state = execute ? target : source;
            BPMNRuntimeInstance scope = getScope(state);
            return ElementsOpen.open(getModel(), state, scope, getBaseElement(), execute);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Open open = (Open) o;
            return getBaseElement().equals(open.getBaseElement());
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), getBaseElement());
        }

        @Override
        public String toString() {
            return "Open " + getBaseElement().getId();
        }
    }

    public static class Close extends Transition {

        public Close(BPMNModelHandler model, BPMNRuntimeState state, BPMNRuntimeInstance scope) {
            super(model, state, scope);
        }

        @Override
        public boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target) {
            boolean execute = target != null;
            BPMNRuntimeState state = execute ? target : source;
            BPMNRuntimeInstance scope = getScope(state);
            return ElementsClose.close(getModel(), state, scope, execute);
        }

        @Override
        public String toString() {
            return "Close " + getScope().getId();
        }

    }

}
