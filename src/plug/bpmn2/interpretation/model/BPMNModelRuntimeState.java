package plug.bpmn2.interpretation.model;

import plug.bpmn2.interpretation.model.instance.data.EventFlowData;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;

import java.util.HashSet;
import java.util.Set;

public class BPMNModelRuntimeState {

    private final BPMNRuntimeInstance root;
    private final Set<MessageFlowData> messageFlowDataSet;
    private final Set<EventFlowData> eventFlowDataSet;

    public BPMNModelRuntimeState(BPMNRuntimeInstance root) {
        this.root = root;
        messageFlowDataSet = new HashSet<>();
        eventFlowDataSet = new HashSet<>();
    }

    public BPMNRuntimeInstance getRoot() {
        return root;
    }

    public Set<MessageFlowData> getMessageFlowDataSet() {
        return messageFlowDataSet;
    }

    public Set<EventFlowData> getEventFlowDataSet() {
        return eventFlowDataSet;
    }
}
