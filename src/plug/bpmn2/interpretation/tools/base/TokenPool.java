package plug.bpmn2.interpretation.tools.base;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.interpretation.model.instance.data.Token;

import java.util.HashMap;
import java.util.Map;

public class TokenPool {

    private final Map<SequenceFlow, Token> sequenceFlowTokenMap;

    public TokenPool() {
        sequenceFlowTokenMap = new HashMap<>();
    }

    public Token getToken(SequenceFlow sequenceFlow) {
        Token result = sequenceFlowTokenMap.get(sequenceFlow);
        if (result == null) {
            result = new Token(sequenceFlow);
            sequenceFlowTokenMap.put(sequenceFlow, result);
        }
        return result;
    }

}
