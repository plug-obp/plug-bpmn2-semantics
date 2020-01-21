package plug.bpmn2.dsim.tools.transitions;

import org.eclipse.bpmn2.IntermediateCatchEvent;
import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.dsim.tools.utils.ElementsMessageFlow;
import plug.bpmn2.dsim.tools.Transition;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
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
        int messageFlowIndex = ElementsMessageFlow.getMessageFlowIndex(source, catchEvent, false);
        if (messageFlowIndex == -1) {
            return false;
        }
        MessageFlowData sourceMessageFlowData = source.getMessageFlowDataList().get(messageFlowIndex);
        if (!sourceMessageFlowData.getData()) {
            return false;
        }
        if (target != null) {
            MessageFlowData targetMessageFlowData = target.getMessageFlowDataList().get(messageFlowIndex);
            FlowElementsContainerInstance scope = (FlowElementsContainerInstance) getScope(target);
            for (SequenceFlow sequenceFlow : catchEvent.getIncoming()) {
                scope.getTokenSet().remove(getModel().tokens.get(sequenceFlow));
            }
            for (SequenceFlow sequenceFlow : catchEvent.getOutgoing()) {
                scope.getTokenSet().add(getModel().tokens.get(sequenceFlow));
            }
            MessageFlowData nextMessageFlowData = new MessageFlowData(
                    targetMessageFlowData.getBaseElement(),
                    targetMessageFlowData.getSourceParent(),
                    targetMessageFlowData.getTargetParent(),
                    false
            );
            target.getMessageFlowDataList().set(messageFlowIndex, nextMessageFlowData);
        }
        return true;
    }
}
