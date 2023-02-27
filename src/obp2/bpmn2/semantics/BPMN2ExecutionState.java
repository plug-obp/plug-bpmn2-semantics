package obp2.bpmn2.semantics;

import obp2.core.defaults.DefaultConfiguration;

import java.util.Arrays;

public class BPMN2ExecutionState extends DefaultConfiguration<BPMN2ExecutionState> {

    public final int[] tokens;

    public BPMN2ExecutionState(int[] tokens) {
        this.tokens = tokens;
    }

    public void reorderTokens(int[] newPositions) {
        int[] oldTokens = Arrays.copyOf(tokens, tokens.length);
        for (int i = 0; i < newPositions.length; i++) {
            tokens[newPositions[i]] = oldTokens[i];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BPMN2ExecutionState that = (BPMN2ExecutionState) o;
        return Arrays.equals(tokens, that.tokens);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tokens);
    }

    @Override
    public BPMN2ExecutionState createCopy() {
        int[] copyTokens = Arrays.copyOf(tokens, tokens.length);
        return new BPMN2ExecutionState(copyTokens);
    }

    @Override
    public String toString() {
        return "BPMN2ExecutionState{" +
                "tokens=" + Arrays.toString(tokens) +
                '}';
    }

}
