package obp2.bpmn2.semantics;

import obp2.bpmn2.model.action.BPMN2FlowAction;

import java.util.*;

public interface BPMN2Semantics {
    Set<BPMN2ExecutionState> execute(BPMN2ExecutionState source, BPMN2FlowAction action);
    boolean canFire(BPMN2ExecutionState source, BPMN2FlowAction action);

}
