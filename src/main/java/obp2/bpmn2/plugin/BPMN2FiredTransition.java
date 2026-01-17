package obp2.bpmn2.plugin;

import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.core.defaults.FiredTransition;

import java.util.Set;

public class BPMN2FiredTransition extends FiredTransition<BPMN2ExecutionState, BPMN2FlowAction, Void>{

    public BPMN2FiredTransition(BPMN2ExecutionState source, BPMN2ExecutionState target, BPMN2FlowAction fired) {
        super(source, target, fired);
    }

    public BPMN2FiredTransition(BPMN2ExecutionState source, Set<BPMN2ExecutionState> targets, BPMN2FlowAction fired) {
        super(source, targets, fired);
    }

}
