package plug.bpmn2.tools.runtime.system.simple.guard;

import plug.bpmn2.tools.BPMNModelHandler;

public class StateIsEmpty extends AbstractGuard {

    public StateIsEmpty(BPMNModelHandler model) {
        super(model);
    }

    @Override
    public boolean evaluate() {
        return getState().isEmpty();
    }

}
