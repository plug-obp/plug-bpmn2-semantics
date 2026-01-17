package plug.bpmn2.semantics.transition.kinds;

import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.state.instance.data.Token;
import plug.bpmn2.semantics.transition.Transition;

public class TransitionGateway extends Transition {
    //TODO: At the moment handle all gateways as parallel gateways, no good

    private final Gateway baseElement;

    public TransitionGateway(BPMNModelHandler model,
                             BPMNRuntimeState state,
                             BPMNRuntimeInstance scope,
                             Gateway baseElement) {
        super(model, state, scope);
        this.baseElement = baseElement;
    }

    @Override
    public Gateway getBaseElement() {
        return baseElement;
    }

    @Override
    public boolean attempt(BPMNRuntimeState source, BPMNRuntimeState target) {
        BPMNRuntimeInstance scope = getScope(source);
        if (!(scope instanceof FlowElementsContainerInstance)) {
            throw new IllegalStateException("Illegal gateway scope (expected flow element container)");
        }
        FlowElementsContainerInstance container = (FlowElementsContainerInstance) scope;
        for (SequenceFlow sequenceFlow : baseElement.getIncoming()) {
            Token token = getModel().tokens.get(sequenceFlow);
            if (!container.getTokenSet().contains(token)) {
                return false;
            }
        }
        if (target != null) {
            FlowElementsContainerInstance targetContainer = (FlowElementsContainerInstance) getScope(target);
            for (SequenceFlow sequenceFlow : baseElement.getIncoming()) {
                Token token = getModel().tokens.get(sequenceFlow);
                targetContainer.getTokenSet().remove(token);
            }
            for (SequenceFlow sequenceFlow : baseElement.getOutgoing()) {
                Token token = getModel().tokens.get(sequenceFlow);
                targetContainer.getTokenSet().add(token);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Go Through " + getModel().printer.getShortString(getBaseElement());
    }

}
