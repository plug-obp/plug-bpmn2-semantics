package obp2.bpmn2.plugin;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.bpmn2.semantics.BPMN2Semantics;
import obp2.bpmn2.semantics.BPMN2SemanticsFlowCompletion;
import obp2.bpmn2.semantics.BPMN2SemanticsNaive;
import obp2.runtime.core.ITransitionRelation;
import obp2.runtime.core.defaults.DefaultLanguageService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class BPMN2TransitionRelation
        extends DefaultLanguageService<BPMN2ExecutionState, BPMN2FlowAction, Void>
        implements ITransitionRelation<BPMN2ExecutionState, BPMN2FlowAction, Void> {

    private final BPMN2ProcessedModel model;
    private final BPMN2Semantics semantics;

    public BPMN2TransitionRelation(BPMN2ProcessedModel model, boolean flowCompletion) {
        this.model = model;
        semantics = flowCompletion ? new BPMN2SemanticsFlowCompletion(model, new BPMN2SemanticsNaive())
                : new BPMN2SemanticsNaive();
    }

    public BPMN2TransitionRelation(BPMN2ProcessedModel model) {
        this(model, true);
    }

    @Override
    public Set<BPMN2ExecutionState> initialConfigurations() {
        BPMN2ExecutionState initial = new BPMN2ExecutionState(new int[0]);
        BPMN2FlowAction initialisation = model.getFlowActionPool().getFlowAction(0);
        return semantics.execute(initial, initialisation);
    }

    @Override
    public Collection<BPMN2FlowAction> fireableTransitionsFrom(BPMN2ExecutionState source) {
        if (source.tokens.length == 0) return Collections.emptySet();
        ArrayList<BPMN2FlowAction> result = new ArrayList<>();
        for (BPMN2FlowAction flowAction : model.getFlowActionPool().getFlowActionArray()) {
            if (semantics.canFire(source, flowAction)) result.add(flowAction);
        }
        return result;
    }

    @Override
    public BPMN2FiredTransition fireOneTransition(BPMN2ExecutionState source, BPMN2FlowAction action) {
        Set<BPMN2ExecutionState> targets = semantics.execute(source, action);
        return new BPMN2FiredTransition(source, targets, action);
    }

}
