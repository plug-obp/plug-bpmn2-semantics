package plug.bpmn2.dsim.tools;

import plug.bpmn2.AbstractTest;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;
import plug.bpmn2.interpretation.model.instance.TaskInstance;
import plug.core.IFiredTransition;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExplorationTest extends AbstractTest {

    static private final int MAX_COUNT = 10000;

    private Set<BPMNRuntimeState> known;

    @Override
    protected void testModel() {
        TransitionRelation relation = new TransitionRelation(getModelHandler());

        known = new HashSet<>();
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

        assertTrue("Explored " + known.size() + " states, aborted.", toSee.isEmpty());
        System.out.println("Successfully explored " + known.size() + " states");
    }

    @Override
    public void process_e0t0e1() {
        super.process_e0t0e1();
        assertEquals(4, known.size());
    }

    @Override
    public void process_CAS_191029() {
        super.process_CAS_191029();
        assertEquals(1, known.size());
    }

    @Override
    public void process_CAS_191030() {
        super.process_CAS_191030();
        assertEquals(1, known.size());
    }

    @Override
    public void process_CAS_191128() {
        super.process_CAS_191128();
        assertEquals(1836, known.size());
    }

    @Override
    public void process_2() {
        super.process_2();
        int size0 = 4;
        int size1 = 2 + (4 * 4);
        int size = size0 * size1;
        assertEquals(size, known.size());
    }

}
