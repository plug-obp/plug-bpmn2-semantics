package obp2.bpmn2.pocs;

import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.plugin.BPMN2Plugin;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.runtime.core.IAtomicPropositionsEvaluator;
import obp2.runtime.core.ILanguageModule;
import obp2.runtime.core.ITransitionRelation;

import java.io.File;
import java.net.URI;
import java.util.Set;

public class GOBP2Plugin {

    static public void main(String args[]) throws Exception {

        URI modelURI = new File(ALoadingFiles.PARALLEL_GATEWAYS_FILE_PATH).toURI();

        String[] atomicPropositions = {
                "t.tokensLength() > 0",
                "t.tokensLength() == 0"
        };

        BPMN2Plugin plugin = new BPMN2Plugin();
        ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> module = plugin.getLanguageModule(modelURI);

        IAtomicPropositionsEvaluator<BPMN2ExecutionState, BPMN2FlowAction, Void> evaluator = module.getAtomicPropositionEvaluator();
        evaluator.setModule(module);
        evaluator.registerAtomicPropositions(atomicPropositions);

        ITransitionRelation<BPMN2ExecutionState, BPMN2FlowAction, Void> transitionRelation = module.getTransitionRelation();
        Set<BPMN2ExecutionState> initials = transitionRelation.initialConfigurations();

        for (BPMN2ExecutionState initial : initials) {
            boolean[] evaluation = evaluator.getAtomicPropositionValuations(initial, null, null, initial);
            for (int i = 0; i < evaluation.length; i++) {
                System.out.println("Atomic proposition " + i + " evaluates to " + evaluation[i]);
            }
        }
    }

}
