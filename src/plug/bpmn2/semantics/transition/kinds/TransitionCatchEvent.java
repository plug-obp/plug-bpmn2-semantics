package plug.bpmn2.semantics.transition.kinds;

import org.eclipse.bpmn2.IntermediateCatchEvent;
import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.transition.Transition;
import plug.bpmn2.semantics.transition.utils.ElementsMessageFlow;

public class TransitionCatchEvent extends Transition {

    private final IntermediateCatchEvent catchEvent;

    public TransitionCatchEvent(BPMNModelHandler model,
                                BPMNRuntimeState state,
                                BPMNRuntimeInstance scope,
                                IntermediateCatchEvent catchEvent) {
        super(model, state, scope);
        this.catchEvent = catchEvent;
    }

    @Override
    public IntermediateCatchEvent getBaseElement() {
        return catchEvent;
    }

    @Override
    public boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target) {
        if (ElementsMessageFlow.attemptToReceive(source, target, catchEvent)) {
            if (target != null) {
                FlowElementsContainerInstance scope = (FlowElementsContainerInstance) getScope(target);
                for (SequenceFlow sequenceFlow : catchEvent.getIncoming()) {
                    scope.getTokenSet().remove(getModel().tokens.get(sequenceFlow));
                }
                for (SequenceFlow sequenceFlow : catchEvent.getOutgoing()) {
                    scope.getTokenSet().add(getModel().tokens.get(sequenceFlow));
                }
            }
            return true;
        }
        return false;
    }
}
