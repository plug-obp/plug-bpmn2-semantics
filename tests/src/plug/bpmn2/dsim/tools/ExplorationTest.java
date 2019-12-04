package plug.bpmn2.dsim.tools;

import plug.bpmn2.AbstractTest;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.core.IFiredTransition;

import java.util.*;

import static org.junit.Assert.*;

public class ExplorationTest extends AbstractTest {

    static private final int MAX_COUNT = 100000;

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
        int size0 = 4;
        int size1 = 10;
        int size2 = 2 + (4 * 4);
        int size3 = 6;
        int size4 = 4;
        int size = size0 * size1 * size2 * size3 * size4; // 17280
        assertEquals(size, known.size());
    }

    @Override
    public void process_2() {
        super.process_2();
        int size0 = 4;
        int size1 = 2 + (4 * 4);
        int size = size0 * size1; // 72
        assertEquals(size, known.size());
    }

    @Override
    public void enstabSpray() {
        try {
            super.enstabSpray();
        } catch (UnsupportedOperationException e) {
            assertTrue(e.getMessage().contains("SubProcess"));
            System.out.println("Unsupported Operation Exception 'SubProcess' thrown as expected");
        }
    }
}
