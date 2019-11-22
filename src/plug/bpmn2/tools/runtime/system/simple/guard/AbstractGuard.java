package plug.bpmn2.tools.runtime.system.simple.guard;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.runtime.system.simple.common.AbstractContext;

public abstract class AbstractGuard extends AbstractContext {

    public abstract boolean evaluate();

    public AbstractGuard(BPMNModelHandler model) {
        super(model);
    }

    public boolean evaluate(BPMNRuntimeState state,
                            BPMNRuntimeInstance scope) {
        setArguments(state, scope);
        boolean result = evaluate();
        clear();
        return result;
    }

}
