package plug.bpmn2.plugin;

import obp2.runtime.core.LanguageModule;
import plug.bpmn2.model.BPMNModelHandler;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.transition.FoldTransitionRelation;
import plug.bpmn2.semantics.transition.Transition;
import plug.bpmn2.semantics.transition.TransitionRelation;

public class BPMNRuntime
        extends LanguageModule<BPMNRuntimeState, Transition, Void> {

    public BPMNRuntime(BPMNModelHandler model, boolean fold) {
        super(
                fold ? new FoldTransitionRelation(model)
                        : new TransitionRelation(model),
                null, new BPMNRuntimeView()
        );
    }

    @Override
    public TransitionRelation getTransitionRelation() {
        return (TransitionRelation) super.getTransitionRelation();
    }

}
