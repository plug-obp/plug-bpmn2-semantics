package plug.bpmn2.tools.runtime.system.simple.common;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;

public class AbstractContext {

    private final BPMNModelHandler model;

    private BPMNRuntimeState state;
    private BPMNRuntimeInstance scope;

    public AbstractContext(BPMNModelHandler model) {
        this.model = model;
    }

    public BPMNRuntimeState getState() {
        return state;
    }

    public BPMNRuntimeInstance getScope() {
        return scope;
    }

    public BPMNModelHandler getModel() {
        return model;
    }

    public void setState(BPMNRuntimeState state) {
        this.state = state;
    }

    public void setScope(BPMNRuntimeInstance scope) {
        this.scope = scope;
    }

    public void setArguments(BPMNRuntimeState state,
                             BPMNRuntimeInstance scope
    ) {
        setState(state);
        setScope(scope);
    }

    public void clear() {
        setState(null);
        setScope(null);
    }

    @Override
    public String toString() {
        return "Context {" +
                "state=" + state +
                ", scope=" + scope +
                '}';
    }

    protected void raiseWarning(String text, Object subject) {
        System.err.println(
                "WARNING [" +
                        this.getClass().getSimpleName() + "]: " +
                        text +
                        " (" + subject + ")" +
                        AbstractContext.this.toString());
    }

}
