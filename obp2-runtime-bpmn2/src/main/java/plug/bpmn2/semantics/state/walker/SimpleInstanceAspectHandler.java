package plug.bpmn2.semantics.state.walker;

import plug.bpmn2.semantics.state.BPMNRuntimeInstance;

import java.util.function.Consumer;

public class SimpleInstanceAspectHandler implements BPMNInstanceAspectHandler {

    private final Consumer<BPMNRuntimeInstance> doEach;

    public SimpleInstanceAspectHandler(Consumer<BPMNRuntimeInstance> doEach) {
        this.doEach = doEach;
    }

    @Override
    public void handleAllInstances(BPMNRuntimeInstance instance) {
        doEach.accept(instance);
    }

}
