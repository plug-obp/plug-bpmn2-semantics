package plug.bpmn2.dsim.tools;

import org.junit.Test;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;
import plug.bpmn2.tools.BPMNLoader;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.core.IFiredTransition;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class TransitionRelationTest {

    private void simpleAssertState(BPMNRuntimeState initialState, int tokens, int children) {
        assertEquals(1, initialState.getRootInstances().size());
        for (BPMNRuntimeInstance instance : initialState.getRootInstances()) {
            assertTrue(instance instanceof ProcessInstance);
            ProcessInstance processInstance = (ProcessInstance) instance;
            assertEquals(tokens, processInstance.getTokenSet().size());
            assertEquals(children, processInstance.getChildInstanceSet().size());
        }
    }

    private BPMNRuntimeState simpleInitial(TransitionRelation relation) {
        Set<BPMNRuntimeState> initialStates = relation.initialConfigurations();
        for (BPMNRuntimeState initialState: initialStates) {
            return initialState;
        }
        return null;
    }

    private Transition simpleTransition(TransitionRelation relation, BPMNRuntimeState source) {
        Collection<Transition> transitions = relation.fireableTransitionsFrom(source);
        for (Transition transition : transitions) {
            return transition;
        }
        return null;
    }

    private BPMNRuntimeState simpleFire(TransitionRelation relation,
                                        Transition transition,
                                        BPMNRuntimeState source) {
        IFiredTransition<BPMNRuntimeState, ?> firedTransition = relation.fireOneTransition(source, transition);
        return firedTransition.getTarget(0);
    }

    @Test
    public void simple() {
        BPMNLoader loader = new BPMNLoader();
        loader.loadModelFromURLString("minimal/process_e0t0e1.bpmn");

        TransitionRelation transitionRelation = new TransitionRelation(loader.getModelHandler());

        BPMNRuntimeState initialState = simpleInitial(transitionRelation);
        simpleAssertState(initialState, 1, 0);

        Transition openTask = simpleTransition(transitionRelation, initialState);
        assertTrue(openTask instanceof Transition.Open);

        BPMNRuntimeState taskActive = simpleFire(transitionRelation, openTask, initialState);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);

        Transition closeTask = simpleTransition(transitionRelation, taskActive);
        assertTrue(closeTask instanceof Transition.Close);

        BPMNRuntimeState taskClosed = simpleFire(transitionRelation, closeTask, taskActive);
        simpleAssertState(taskClosed, 1, 0);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);

        Transition closeProcess = simpleTransition(transitionRelation, taskClosed);
        assertTrue(closeProcess instanceof Transition.Close);

        BPMNRuntimeState finalState = simpleFire(transitionRelation, closeProcess, taskClosed);
        assertTrue(finalState.isEmpty());
        simpleAssertState(taskClosed, 1, 0);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);

        assertTrue(transitionRelation.fireableTransitionsFrom(finalState).isEmpty());
        assertTrue(finalState.isEmpty());
        simpleAssertState(taskClosed, 1, 0);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);
    }

}