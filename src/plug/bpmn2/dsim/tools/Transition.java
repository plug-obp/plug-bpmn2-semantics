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

    public abstract BaseElement getBaseElement();

    public abstract boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target);

    public boolean guard(BPMNRuntimeState source) {
        return attempt(source, null);
    }

    public BPMNRuntimeState execute(BPMNRuntimeState source) {
        BPMNRuntimeState target = source.createCopy();
        attempt(source, target);
        return target;
    }

}
