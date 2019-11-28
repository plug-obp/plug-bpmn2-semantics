package plug.bpmn2.interpretation.model;

import plug.bpmn2.interpretation.model.instance.data.EventFlowData;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;

import java.util.*;

public class BPMNRuntimeState {

    private final Set<BPMNRuntimeInstance> rootInstances;
    private final Set<MessageFlowData> messageFlowDataSet;
    private final Set<EventFlowData> eventFlowDataSet;
    private final List<Object> intermediateFlagList;

    public BPMNRuntimeState() {
        rootInstances = new HashSet<>();
        messageFlowDataSet = new HashSet<>();
        eventFlowDataSet = new HashSet<>();
        intermediateFlagList = new LinkedList<>();
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

    public List<Object> getIntermediateFlagList() {
        return intermediateFlagList;
    }

    public boolean isEmpty() {
        return rootInstances.isEmpty() &&
                messageFlowDataSet.isEmpty() &&
                eventFlowDataSet.isEmpty() &&
                intermediateFlagList.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BPMNRuntimeState that = (BPMNRuntimeState) o;
        return getRootInstances().equals(that.getRootInstances()) &&
                getMessageFlowDataSet().equals(that.getMessageFlowDataSet()) &&
                getEventFlowDataSet().equals(that.getEventFlowDataSet()) &&
                getIntermediateFlagList().equals(that.getIntermediateFlagList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRootInstances(), getMessageFlowDataSet(), getEventFlowDataSet(), getIntermediateFlagList());
    }
    
}
