package obp2.bpmn2.semantics;

import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.utils.BPMN2TokenUtils;

import java.util.*;

/**
 * Stateless implementation of BPMN2Semantics that is naive in
 * the sense that, for each execute, it creates three sets to
 * keep track of removed, optional and produced tokens.
 *
 * It is possible to be a lot more efficient but primarily
 * results gave decent performances already.
 */
public class BPMN2SemanticsNaive implements BPMN2Semantics {
    @Override
    public Set<BPMN2ExecutionState> execute(BPMN2ExecutionState source, BPMN2FlowAction flowAction) {
        int[] sourceTokens = source.tokens;
        Set<Integer> removeOnce = new HashSet<>();
        for (int tokenToRemoveOnce : flowAction.getMandatoryTokens()) {
            removeOnce.add(tokenToRemoveOnce);
        }
        Set<Integer> optionalTokens = new HashSet<>();
        for (int optionalToken : flowAction.getOptionalTokens()) {
            optionalTokens.add(optionalToken);
        }
        List<Integer> targetTokens = new ArrayList<>();
        for (int producedToken : flowAction.getProducedTokens()) {
            targetTokens.add(producedToken);
        }
        for (int sourceToken : sourceTokens) {
            if (removeOnce.contains(sourceToken)) {
                removeOnce.remove(sourceToken);
            } else if (optionalTokens.contains(sourceToken)) {
                for (int optionalProducedToken : flowAction.getProducedTokens(sourceToken)) {
                    targetTokens.add(optionalProducedToken);
                }
            } else {
                targetTokens.add(sourceToken);
            }
        }
        if (removeOnce.size() != 0) {
            throw new IllegalStateException("Missing tokens");
        }
        targetTokens.sort(Integer::compareTo);
        return Collections.singleton(new BPMN2ExecutionState(BPMN2TokenUtils.toArray(targetTokens)));
    }

    @Override
    public boolean canFire(BPMN2ExecutionState state, BPMN2FlowAction flowAction) {
        int[] tokens = state.tokens;
        int[] mandatoryTokens = flowAction.getMandatoryTokens();

        // TODO change this once implicit StartEvent are okay
        // Other important cases to think about: INITIALISATION, disconnected model elements
        if (mandatoryTokens.length == 0) return false;

        for (int mandatoryToken : mandatoryTokens) {
            if (!BPMN2TokenUtils.hasToken(tokens, mandatoryToken)) return false;
        }

        int[] excludedTokens = flowAction.getExcludedTokens();
        for (int excludedToken : excludedTokens) {
            if (BPMN2TokenUtils.hasToken(tokens, excludedToken)) return false;
        }

        return true;
    }

}
