package plug.bpmn2.tools.runtime.system.simple.command;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.runtime.system.simple.common.AbstractContext;

public abstract class AbstractCommand extends AbstractContext {

    public abstract void execute();

    public AbstractCommand(BPMNModelHandler model) {
        super(model);
    }

    public void execute(BPMNRuntimeState state,
                        BPMNRuntimeInstance scope) {
        setArguments(state, scope);
        execute();
        clear();
    }

}
