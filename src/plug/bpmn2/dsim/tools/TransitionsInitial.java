package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

class TransitionsInitial {

    static private void openInstances(BPMNModelHandler model, BPMNRuntimeState initialState) {
        for (EObject rootObject : model.ownership.getFloorSet(0)) {
            if (rootObject instanceof Collaboration || rootObject instanceof Process) {
                BaseElement rootElement = (BaseElement) rootObject;
                ElementsOpen.open(model, initialState, null, rootElement, true);
            }
        }
    }

    static private void addMessageFlowData(BPMNModelHandler model, BPMNRuntimeState initialState) {
        for (MessageFlow messageFlow : ElementsMessageFlow.getMessageFlows(model)) {
            MessageFlowData messageFlowData = ElementsMessageFlow.createMessageFlowData(
                    initialState,
                    messageFlow,
                    false
            );
            if (messageFlowData != null) {
                initialState.getMessageFlowDataList().add(messageFlowData);
            }
        }
    }

    static public Set<BPMNRuntimeState> get(BPMNModelHandler model) {
        BPMNRuntimeState initialState = new BPMNRuntimeState();
        openInstances(model, initialState);
        addMessageFlowData(model, initialState);
        return Collections.singleton(initialState);
    }

}
