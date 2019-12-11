package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ElementsMessageFlow {

    static public Collection<MessageFlow> getMessageFlows(BPMNModelHandler model) {
        return model.containment.getNodeSet().stream()
                .filter(object -> object instanceof MessageFlow)
                .map(object -> (MessageFlow) object)
                .collect(Collectors.toList());
    }

    static private BPMNRuntimeInstance getOwner(List<BPMNRuntimeInstance> instanceList, EObject container) {
        for (BPMNRuntimeInstance instance : instanceList) {
            if (instance.getBaseElement() == container) {
                return instance;
            }
            BPMNRuntimeInstance childOwner = getOwner(instance.getChildInstanceList(), container);
            if (childOwner != null) {
                return childOwner;
            }
        }
        return null;
    }

    static public BPMNRuntimeInstance getOwner(BPMNRuntimeState state, InteractionNode interactionNode) {
        EObject container = interactionNode.eContainer();
        return getOwner(state.getRootInstanceList(), container);
    }

    static public BaseElement getBaseElement(InteractionNode interactionNode) {
        return (BaseElement) interactionNode;
    }

    static public MessageFlowData createMessageFlowData(BPMNRuntimeState state,
                                                        MessageFlow messageFlow,
                                                        boolean isPresent) {
        BPMNRuntimeInstance sourceOwner = getOwner(state, messageFlow.getSourceRef());
        if (sourceOwner == null) return null;
        BPMNRuntimeInstance targetOwner = getOwner(state, messageFlow.getTargetRef());
        if (targetOwner == null) return null;
        return new MessageFlowData(
                messageFlow,
                sourceOwner,
                targetOwner,
                isPresent
        );
    }

    static public int getMessageFlowIndex(BPMNRuntimeState state,
                                          BaseElement baseElement,
                                          boolean source) {
        for (int i = 0; i < state.getMessageFlowDataList().size(); i++) {
            MessageFlowData currentMessageFlowData = state.getMessageFlowDataList().get(i);
            InteractionNode currentInteractionNode = source ?
                    currentMessageFlowData.getBaseElement().getSourceRef() :
                    currentMessageFlowData.getBaseElement().getTargetRef();
            BaseElement currentBaseElement = getBaseElement(currentInteractionNode);
            if (baseElement == currentBaseElement) {
                return i;
            }
        }
        return -1;
    }

}
