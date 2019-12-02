package plug.bpmn2.interpretation.model;

import plug.bpmn2.interpretation.model.instance.data.EventFlowData;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
import plug.bpmn2.interpretation.model.utils.BPMNRuntimeStateCopy;
import plug.core.defaults.DefaultConfiguration;

import java.util.*;

public class BPMNRuntimeState extends DefaultConfiguration<BPMNRuntimeState> {

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
    public BPMNRuntimeState createCopy() {
        return new BPMNRuntimeStateCopy().copy(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BPMNRuntimeState that = (BPMNRuntimeState) o;
        if (getRootInstanceList().size() != that.getRootInstanceList().size()) return false;
        if (!getRootInstanceList().containsAll(that.getRootInstanceList())) return false;
        if (!that.getRootInstanceList().containsAll(that.getRootInstanceList())) return false;
        // TODO {a, a, b} == {a, b, b} ... Strengthen it.
        // TODO test other lists too
        return true;
    }
    
}
