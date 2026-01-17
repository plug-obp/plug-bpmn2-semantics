package obp2.bpmn2.semantics;

public class BPMN2ExecutionStep {
    public int[] actionIndexes;
    public int[] tokenPositionsToRemove;
    public int[] tokenIndexesToAdd;

    public BPMN2ExecutionStep(int[] actionIndexes, int[] tokenPositionsToRemove, int[] tokenIndexesToAdd) {
        this.actionIndexes = actionIndexes;
        this.tokenPositionsToRemove = tokenPositionsToRemove;
        this.tokenIndexesToAdd = tokenIndexesToAdd;
    }

}
