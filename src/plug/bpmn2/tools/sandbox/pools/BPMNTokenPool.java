package plug.bpmn2.tools.sandbox.pools;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.interpretation.model.instance.data.Token;
import java.util.HashMap;
import java.util.Map;

public class BPMNTokenPool {

    private Map<SequenceFlow, Token> sequenceFlowTokenMap = new HashMap<>();

    public Token get(SequenceFlow sequenceFlow) {
        return sequenceFlowTokenMap.computeIfAbsent(
                sequenceFlow,
                (sf) -> new Token(sf)
        );
    }

}
