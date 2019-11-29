package plug.bpmn2.dsim.tools;

import plug.bpmn2.AbstractTest;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.core.IFiredTransition;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ExplorationTest extends AbstractTest {

    static private final int MAX_COUNT = 1000;

    @Override
    protected void testModel() {
        TransitionRelation relation = new TransitionRelation(getModelHandler());

        Set<BPMNRuntimeState> known = new HashSet<>();
        LinkedList<BPMNRuntimeState> toSee = new LinkedList<>();

        known.addAll(relation.initialConfigurations());
        toSee.addAll(known);

        int count = known.size();

        while (!toSee.isEmpty() && count < MAX_COUNT) {
            BPMNRuntimeState source = toSee.removeFirst();
            Collection<Transition> outgoingTransitions = relation.fireableTransitionsFrom(source);
            for (Transition outgoingTransition : outgoingTransitions) {
                IFiredTransition<BPMNRuntimeState, ?> firedTransition = relation.fireOneTransition(
                        source, outgoingTransition
                );
                for (BPMNRuntimeState target : firedTransition.getTargets()) {
                    if (known.add(target)) {
                        toSee.add(target);
                        count++;
                    }
                }
            }
        }

        assertTrue(toSee.isEmpty());

    }

}
