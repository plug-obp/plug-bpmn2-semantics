package plug.bpmn2.model.pool;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.semantics.state.instance.data.Token;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BPMNTokenPool {

    private Map<SequenceFlow, Token> sequenceFlowTokenMap = new HashMap<>();

    public Token get(SequenceFlow sequenceFlow) {
        return sequenceFlowTokenMap.computeIfAbsent(
                sequenceFlow,
                (sf) -> new Token(sf)
        );
    }

    public Collection<Token> get(Collection<SequenceFlow> sequenceFlowCollection) {
        return sequenceFlowCollection.stream()
                .map(this::get)
                .collect(Collectors.toSet());
    }

    public Collection<Token> getAll() {
        return sequenceFlowTokenMap.values();
    }

}
