package plug.bpmn2.dsim.example;


import plug.bpmn2.dsim.module.BPMNPlugin;
import plug.bpmn2.dsim.tools.Transition;
import plug.bpmn2.dsim.tools.TransitionRelation;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.core.IFiredTransition;

import java.util.Collection;
import java.util.Set;

/**
 * Another good example would be {@link plug.bpmn2.dsim.tools.TransitionRelationTest}
 */
public class SimpleMain {

    static public void main(String[] args) throws Exception {
        String fileName = args.length == 0 ? "minimal/process_e0t0e1.bpmn" : args[0];
        BPMNPlugin plugin = new BPMNPlugin();
        TransitionRelation relation = plugin.getLoader().getRuntime(fileName);

        // Get initial states, as of now always a singleton
        Set<BPMNRuntimeState> initialStates = relation.initialConfigurations();
        BPMNRuntimeState initialState = initialStates.iterator().next();

        // Get outgoing transitions
        Collection<Transition> outgoingTransitions = relation.fireableTransitionsFrom(initialState);

        for (Transition transition : outgoingTransitions) {
            // Fire one transition
            IFiredTransition<BPMNRuntimeState, ?> firedTransition = relation.fireOneTransition(initialState, transition);
            // Access the successors, as of now should always be a singleton.
            BPMNRuntimeState target = firedTransition.getTarget(0);
        }
    }

}
