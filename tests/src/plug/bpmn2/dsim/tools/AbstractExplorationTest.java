package plug.bpmn2.dsim.tools;

import plug.bpmn2.AbstractTest;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.core.IFiredTransition;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public abstract class AbstractExplorationTest extends AbstractTest {

    static private final int MAX_COUNT = 100000;

    TransitionRelation relation;
    private Set<Integer> knownHashSet;
    private Set<BPMNRuntimeState> known;
    LinkedList<BPMNRuntimeState> toSee;
    private int hashCollisionCount;
    private int transitionCount;

    protected abstract TransitionRelation createTransitionRelation();

    public int getSize() {
        return known.size();
    }

    public int getTransitionCount() {
        return transitionCount;
    }

    protected void init() {
        relation = createTransitionRelation();
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

}
