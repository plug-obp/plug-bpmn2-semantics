package obp2.bpmn2.pocs;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.bpmn2.semantics.BPMN2Semantics;
import obp2.bpmn2.semantics.BPMN2SemanticsNaive;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;

public class EAFlowCompletionExploration {

    static BPMN2Semantics semantics = new BPMN2SemanticsNaive();

    static public boolean isFlowAction(BPMN2FlowAction flowAction) {
        switch (flowAction.getType()) {
            case END_TASK:
            case CLOSE_PROCESS:
            case INITIALISATION: return false;
            default: return true;
        }
    }

    static public BPMN2ExecutionState flowCompletionNext(
            BPMN2ExecutionState source,
            BPMN2FlowAction action,
            BPMN2ProcessedModel model) {
        BPMN2ExecutionState target = semantics.execute(source, action).stream().findAny().get();
        boolean hasFlowAction = true;
        flowCompletion:
        while (hasFlowAction) {
            hasFlowAction = false;
            for (BPMN2FlowAction flowAction : model.getFlowActionPool().getFlowActionArray()) {
                if (target.tokens.length > 0 && isFlowAction(flowAction) && semantics.canFire(target, flowAction)) {
                    hasFlowAction = true;
                    target = semantics.execute(target, flowAction).stream().findAny().get();
                    continue flowCompletion;
                }
            }
        }
        return target;
    }

    static public void pocFlowCompletionExploration(BPMN2ProcessedModel model, boolean bfs, int cap) {
        DANaiveExploration.pocExploration(model, bfs, cap, EAFlowCompletionExploration::flowCompletionNext);
    }

    static public void main(String[] args) {
        System.out.println("Loading ...");

        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        BPMN2ProcessedModel model = new BPMN2ProcessedModel(documentRoot);

        System.out.println("Exploring ...");

        pocFlowCompletionExploration(model, true, 10000);
        pocFlowCompletionExploration(model, false, 10000);

    }

}
