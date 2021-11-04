package plug.bpmn2.semantics.transition.utils;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Process;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.data.MessageFlowData;

import java.util.Collections;
import java.util.Set;

public class TransitionsInitial {

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
