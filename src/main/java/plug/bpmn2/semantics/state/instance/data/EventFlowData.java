package plug.bpmn2.semantics.state.instance.data;

import org.eclipse.bpmn2.Event;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;

public class EventFlowData extends FlowDataBase {

    private final Event event;
    private final boolean isPresent;

    public EventFlowData(
            Event event,
            BPMNRuntimeInstance sourceParent,
            BPMNRuntimeInstance targetParent,
            boolean isPresent
    ) {
        super(sourceParent, targetParent);
        this.event = event;
        this.isPresent = isPresent;
    }

    @Override
    public Event getBaseElement() {
        return event;
    }

    @Override
    public Boolean getData() {
        return isPresent;
    }

}
