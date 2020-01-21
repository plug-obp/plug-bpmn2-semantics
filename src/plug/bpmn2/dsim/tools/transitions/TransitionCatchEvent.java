package plug.bpmn2.dsim.tools.transitions;

import org.eclipse.bpmn2.IntermediateCatchEvent;
import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.dsim.tools.utils.ElementsMessageFlow;
import plug.bpmn2.dsim.tools.Transition;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.tools.BPMNModelHandler;

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
