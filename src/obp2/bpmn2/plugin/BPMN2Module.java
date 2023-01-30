package obp2.bpmn2.plugin;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.runtime.core.*;

public class BPMN2Module extends LanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> {

    public BPMN2Module(BPMN2ProcessedModel model, boolean flowCompletion) {
        super(
          new BPMN2TransitionRelation(model, flowCompletion),
          new BPMN2Evaluator(model),
          new BPMN2View(model)
        );
    }

    @Override
    public BPMN2TransitionRelation getTransitionRelation() {
        return (BPMN2TransitionRelation) super.getTransitionRelation();
    }

}
