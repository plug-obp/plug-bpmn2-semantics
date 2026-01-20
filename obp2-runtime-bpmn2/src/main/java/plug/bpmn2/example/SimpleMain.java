package plug.bpmn2.example;


import plug.bpmn2.model.printer.BPMNModelPrinter;
import plug.bpmn2.plugin.BPMNPlugin;
import plug.bpmn2.plugin.BPMNRuntime;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.transition.BPMNFiredTransition;
import plug.bpmn2.semantics.transition.Transition;
import plug.bpmn2.semantics.transition.TransitionRelation;

import java.util.Collection;
import java.util.Set;

public class SimpleMain {

    static public void main(String[] args) throws Exception {
        String fileName = args.length == 0 ? "tests/process_1.bpmn" : args[0];
        BPMNPlugin plugin = new BPMNPlugin();
        BPMNRuntime runtime = plugin.getRuntime(fileName);
        TransitionRelation relation = runtime.getTransitionRelation();

        BPMNModelPrinter modelPrinter = new BPMNModelPrinter();
        System.out.println(modelPrinter.getString(relation.getModel().documentRoot));

        // Get initial states, as of now always a singleton
        Set<BPMNRuntimeState> initialStates = relation.initialConfigurations();
        BPMNRuntimeState initialState = initialStates.iterator().next();

        // Get outgoing transitions
        Collection<Transition> outgoingTransitions = relation.fireableTransitionsFrom(initialState);

        for (Transition transition : outgoingTransitions) {
            // Fire one transition
            BPMNFiredTransition firedTransition = relation.fireOneTransition(initialState, transition);
            // Access the successors, as of now should always be a singleton (if fold option is true).
            BPMNRuntimeState target = firedTransition.getTarget(0);
        }
    }

}
