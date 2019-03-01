package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.interpretation.model.BPMNRuntimeToken;

import java.util.HashMap;
import java.util.Map;

class TokenPool {

    private final Map<SequenceFlow, BPMNRuntimeToken> sequenceFlowTokenMap;

    TokenPool() {
        sequenceFlowTokenMap = new HashMap<>();
    }

    BPMNRuntimeToken getToken(SequenceFlow sequenceFlow) {
        BPMNRuntimeToken result = sequenceFlowTokenMap.get(sequenceFlow);
        if (result == null) {
            result = new BPMNRuntimeToken(sequenceFlow);
            sequenceFlowTokenMap.put(sequenceFlow, result);
        }
        return result;
    }

}
