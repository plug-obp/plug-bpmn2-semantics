package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;

public class TransitionClose extends Transition {

    private final BaseElement baseElement;

    public TransitionClose(BPMNModelHandler model, BPMNRuntimeState state, BPMNRuntimeInstance scope) {
        super(model, state, scope);
        baseElement = scope.getBaseElement();
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
        return ElementsClose.close(getModel(), state, scope, execute);
    }

    @Override
    public String toString() {
        return "Close " + getModel().printer.getShortString(baseElement);
    }

}
