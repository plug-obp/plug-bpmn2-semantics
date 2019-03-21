package plug.bpmn2.interpretation.model;

import plug.bpmn2.interpretation.model.instance.data.EventFlowData;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;

import java.util.HashSet;
import java.util.Set;

public class BPMNModelRuntimeState {

    private final Set<BPMNRuntimeInstance> rootInstances;
    private final Set<MessageFlowData> messageFlowDataSet;
    private final Set<EventFlowData> eventFlowDataSet;

    public BPMNModelRuntimeState() {
        rootInstances = new HashSet<>();
        messageFlowDataSet = new HashSet<>();
        eventFlowDataSet = new HashSet<>();
    }

    public Set<BPMNRuntimeInstance> getRootInstances() {
        return rootInstances;
    }

    public Set<MessageFlowData> getMessageFlowDataSet() {
        return messageFlowDataSet;
    }

    public Set<EventFlowData> getEventFlowDataSet() {
        return eventFlowDataSet;
    }
}
