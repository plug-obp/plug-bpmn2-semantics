package obp2.bpmn2.model.signal;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.StartEvent;

@FunctionalInterface
public interface SignalIdStrategy {

    Object getSignalId(Event event);

    static String byName(Event event) {
        if (event instanceof StartEvent || event instanceof EndEvent) return null;
        return event.getName().intern();
    }

}
