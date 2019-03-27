package plug.bpmn2.interpretation.model.instance.data;

import org.eclipse.bpmn2.Event;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

public class EventFlowData extends FlowDataBase {

    private final Event event;
    private final boolean isPresent;

    public EventFlowData(
            Event event,
            BPMNRuntimeInstance sourceRef,
            BPMNRuntimeInstance targetRef,
            boolean isPresent
    ) {
        super(sourceRef, targetRef);
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
