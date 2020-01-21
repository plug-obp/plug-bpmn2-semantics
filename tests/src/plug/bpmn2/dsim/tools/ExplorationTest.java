package plug.bpmn2.dsim.tools;

import org.junit.Test;
import plug.bpmn2.AbstractTest;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.core.IFiredTransition;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExplorationTest extends AbstractTest {

    static private final int MAX_COUNT = 100000;

    TransitionRelation relation;
    private Set<Integer> knownHashSet;
    private Set<BPMNRuntimeState> known;
    LinkedList<BPMNRuntimeState> toSee;
    private int hashCollisionCount;
    private int transitionCount;

    protected void init() {
        relation = new TransitionRelation(getModelHandler());
        knownHashSet = new HashSet<>();
        known = new HashSet<>();
        toSee = new LinkedList<>();
        transitionCount = 0;
        hashCollisionCount = 0;
    }

    protected boolean registerState(BPMNRuntimeState target) {
        if (known.add(target)) {
            if (!knownHashSet.add(target.hashCode())) {
                hashCollisionCount += 1;
            }
            toSee.add(target);
            return true;
        }
        return false;
    }

    protected void registerInitialStates() {
        for (BPMNRuntimeState initialState : relation.initialConfigurations()) {
            registerState(initialState);
        }
    }

    protected BPMNRuntimeState getNext() {
        return toSee.removeLast();
    }

    protected boolean isFinished() {
        return toSee.isEmpty() || known.size() >= MAX_COUNT;
    }

    protected String getMetricsString() {
        return "Metrics: " +
                known.size() + " states, " +
                transitionCount + " transitions, " +
                hashCollisionCount + " hash collisions.";
    }

    protected Collection<BPMNRuntimeState> getTargets(BPMNRuntimeState source, Transition transition) {
        IFiredTransition<BPMNRuntimeState, ?> firedTransition = relation.fireOneTransition(
                source, transition
        );
        return firedTransition.getTargets();
    }

    @Override
    protected void testModel() {
        init();
        registerInitialStates();
        while (!isFinished()) {
            BPMNRuntimeState source = getNext();
            Collection<Transition> outgoingTransitions = relation.fireableTransitionsFrom(source);
            for (Transition transition : outgoingTransitions) {
                Collection<BPMNRuntimeState> targets = getTargets(source, transition);
                for (BPMNRuntimeState target : targets) {
                    transitionCount += 1;
                    registerState(target);
                }
            }
        }

        assertTrue("Explored " + known.size() + " states, aborted.", toSee.isEmpty());
        System.out.println("Exploration successful.");
        System.out.println(getMetricsString());
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void mutex() {
        super.mutex();
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
        assertEquals(80832, transitionCount);
    }

    @Override
    public void process_2() {
        super.process_2();
        int size0 = 4;
        int size1 = 2 + (4 * 4);
        int size = size0 * size1; // 72
        assertEquals(size, known.size());
        assertEquals(158, transitionCount);
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

    @Override
    public void messages_1to1() {
        super.messages_1to1();
        assertEquals(3, known.size());
    }
}
