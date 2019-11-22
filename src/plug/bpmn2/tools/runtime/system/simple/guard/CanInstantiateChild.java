package plug.bpmn2.tools.runtime.system.simple.guard;

import plug.bpmn2.tools.BPMNModelHandler;

public class CanInstantiateChild extends AbstractGuard {

    public CanInstantiateChild(BPMNModelHandler model) {
        super(model);
    }

    @Override
    public boolean evaluate() {
        return true;
    }

}
