package plug.bpmn2.interpretation.model;

import plug.bpmn2.interpretation.model.instance.data.EventFlowData;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;

import java.util.*;

public class BPMNRuntimeState {

    private final List<BPMNRuntimeInstance> rootInstanceList;
    private final List<MessageFlowData> messageFlowDataList;
    private final List<EventFlowData> eventFlowDataList;
    private final List<Object> intermediateFlagList;

    public BPMNRuntimeState() {
        rootInstanceList = new LinkedList<>();
        messageFlowDataList = new LinkedList<>();
        eventFlowDataList = new LinkedList<>();
        intermediateFlagList = new LinkedList<>();
    }

    public List<BPMNRuntimeInstance> getRootInstanceList() {
        return rootInstanceList;
    }

    public List<MessageFlowData> getMessageFlowDataList() {
        return messageFlowDataList;
    }

    public List<EventFlowData> getEventFlowDataList() {
        return eventFlowDataList;
    }

    public List<Object> getIntermediateFlagList() {
        return intermediateFlagList;
    }

    public boolean isEmpty() {
        return rootInstanceList.isEmpty() &&
                messageFlowDataList.isEmpty() &&
                eventFlowDataList.isEmpty() &&
                intermediateFlagList.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BPMNRuntimeState that = (BPMNRuntimeState) o;
        return getRootInstanceList().equals(that.getRootInstanceList()) &&
                getMessageFlowDataList().equals(that.getMessageFlowDataList()) &&
                getEventFlowDataList().equals(that.getEventFlowDataList()) &&
                getIntermediateFlagList().equals(that.getIntermediateFlagList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRootInstanceList(), getMessageFlowDataList(), getEventFlowDataList(), getIntermediateFlagList());
    }
    
}
