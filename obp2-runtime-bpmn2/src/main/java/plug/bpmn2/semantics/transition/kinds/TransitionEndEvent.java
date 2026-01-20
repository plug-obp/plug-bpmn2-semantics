package plug.bpmn2.semantics.transition.kinds;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.state.instance.data.Token;
import plug.bpmn2.semantics.transition.Transition;

public class TransitionEndEvent extends Transition {

    private final EndEvent endEvent;

    public TransitionEndEvent(BPMNModelHandler model,
                              BPMNRuntimeState state,
                              BPMNRuntimeInstance scope,
                              EndEvent endEvent) {
        super(model, state, scope);
        this.endEvent = endEvent;
    }

    @Override
    public EndEvent getBaseElement() {
        return endEvent;
    }

    @Override
    public boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target) {
        if (target != null) {
            BPMNRuntimeInstance scope = getScope(target);
            if (!(scope instanceof FlowElementsContainerInstance)) {
                throw new IllegalStateException("Expected a flow elements container");
            }
            FlowElementsContainerInstance flowElementsContainerInstance = (FlowElementsContainerInstance) scope;
            for (SequenceFlow sequenceFlow : endEvent.getIncoming()) {
                Token token = getModel().tokens.get(sequenceFlow);
                flowElementsContainerInstance.getTokenSet().remove(token);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "EndEvent " + getModel().printer.getShortString(endEvent);
    }
}
