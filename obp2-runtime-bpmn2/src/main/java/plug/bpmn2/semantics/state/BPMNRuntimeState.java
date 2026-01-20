package plug.bpmn2.semantics.state;

import obp2.core.defaults.DefaultConfiguration;
import plug.bpmn2.semantics.state.instance.data.EventFlowData;
import plug.bpmn2.semantics.state.instance.data.MessageFlowData;
import plug.bpmn2.semantics.state.utils.BPMNRuntimeCopy;
import plug.bpmn2.semantics.state.utils.BPMNRuntimeEquals;
import plug.bpmn2.semantics.state.utils.BPMNRuntimeHashCode;

import java.util.LinkedList;
import java.util.List;

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
        return new BPMNRuntimeCopy().copy(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BPMNRuntimeState)) return false;
        BPMNRuntimeState that = (BPMNRuntimeState) o;
        return BPMNRuntimeEquals.stateEquals(this, that);
    }

    @Override
    public int hashCode() {
        return BPMNRuntimeHashCode.hashState(this);
    }

}
