package obp2.bpmn2.model.signal;

import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.Event;

import java.util.HashMap;
import java.util.Map;

public class SignalData {

    private final SignalIdStrategy signalIdStrategy;
    private final Map<Object, SignalReferences> signalReferencesMap = new HashMap<>();

    public Map<Object, SignalReferences> getSignalReferencesMap() {
        return signalReferencesMap;
    }

    public SignalData(SignalIdStrategy signalIdStrategy) {
        this.signalIdStrategy = signalIdStrategy;
    }

    public Object getSignal(Event event) {
        return signalIdStrategy.getSignalId(event);
    }

    public CatchEvent[] getCatchEvents(Object signal) {
        SignalReferences signalReferences = signalReferencesMap.get(signal);
        if (signalReferences == null) return new CatchEvent[0];
        return signalReferences.getCatchEvents();
    }

}
