package obp2.bpmn2.semantics;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;

import java.util.HashSet;
import java.util.Set;

public class BPMN2SemanticsFlowCompletion implements BPMN2Semantics {

    private final BPMN2ProcessedModel model;
    private final BPMN2Semantics semantics;

    public BPMN2SemanticsFlowCompletion(BPMN2ProcessedModel model, BPMN2Semantics semantics) {
        this.model = model;
        this.semantics = semantics;
    }

    static public boolean isFlowAction(BPMN2FlowAction flowAction) {
        switch (flowAction.getType()) {
            case END_TASK:
            case THROW_SIGNAL: return false;
            default: return true;
        }
    }

    @Override
    public Set<BPMN2ExecutionState> execute(BPMN2ExecutionState source, BPMN2FlowAction flowAction) {
        Set<BPMN2ExecutionState> naiveTargets = semantics.execute(source, flowAction);
        Set<BPMN2ExecutionState> completedTargets = new HashSet<>();
        for (BPMN2ExecutionState naiveTarget : naiveTargets) {
            BPMN2ExecutionState completedTarget = naiveTarget;
            boolean hasFlowAction = true;
            flowCompletion:
            while (hasFlowAction) {
                hasFlowAction = false;
                for (BPMN2FlowAction interAction : model.getFlowActionPool().getFlowActionArray()) {
                    if (completedTarget.tokens.length > 0 &&
                            isFlowAction(interAction) &&
                            semantics.canFire(completedTarget, interAction)) {
                        Set<BPMN2ExecutionState> newTargets = semantics.execute(completedTarget, interAction);
                        if (newTargets.size() == 0) continue;
                        hasFlowAction = true;
                        completedTarget = newTargets.stream().findAny().get();
                        continue flowCompletion;
                    }
                }
            }
            completedTargets.add(completedTarget);
        }
        return completedTargets;
    }

    @Override
    public boolean canFire(BPMN2ExecutionState source, BPMN2FlowAction action) {
        return semantics.canFire(source, action);
    }

}
