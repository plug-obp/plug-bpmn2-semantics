package obp2.bpmn2.model.signal;

import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.ThrowEvent;

public class SignalReferences {

    private final Object signal;
    private final ThrowEvent[] throwEvents;
    private final CatchEvent[] catchEvents;

    public SignalReferences(Object signal, ThrowEvent[] throwEvents, CatchEvent[] catchEvents) {
        this.signal = signal;
        this.throwEvents = throwEvents;
        this.catchEvents = catchEvents;
    }

    public Object getSignal() {
        return signal;
    }

    public ThrowEvent[] getThrowEvents() {
        return throwEvents;
    }

    public CatchEvent[] getCatchEvents() {
        return catchEvents;
    }

}
