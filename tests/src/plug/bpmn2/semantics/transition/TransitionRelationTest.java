package plug.bpmn2.semantics.transition;

import org.junit.Test;
import plug.bpmn2.semantics.transition.kinds.TransitionClose;
import plug.bpmn2.semantics.transition.kinds.TransitionEndEvent;
import plug.bpmn2.semantics.transition.kinds.TransitionOpen;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.ProcessInstance;
import plug.bpmn2.model.BPMNLoader;
import obp2.core.IFiredTransition;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class TransitionRelationTest {

    private void simpleAssertState(BPMNRuntimeState initialState, int tokens, int children) {
        assertEquals(1, initialState.getRootInstanceList().size());
        for (BPMNRuntimeInstance instance : initialState.getRootInstanceList()) {
            assertTrue(instance instanceof ProcessInstance);
            ProcessInstance processInstance = (ProcessInstance) instance;
            assertEquals(tokens, processInstance.getTokenSet().size());
            assertEquals(children, processInstance.getChildInstanceList().size());
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
        IFiredTransition<BPMNRuntimeState, Transition, Void> firedTransition = relation.fireOneTransition(source, transition);
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
        assertTrue(openTask instanceof TransitionOpen);

        BPMNRuntimeState taskActive = simpleFire(transitionRelation, openTask, initialState);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);

        Transition closeTask = simpleTransition(transitionRelation, taskActive);
        assertTrue(closeTask instanceof TransitionClose);

        BPMNRuntimeState taskClosed = simpleFire(transitionRelation, closeTask, taskActive);
        simpleAssertState(taskClosed, 1, 0);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);

        Transition closeProcess = simpleTransition(transitionRelation, taskClosed);
        assertTrue(closeProcess instanceof TransitionEndEvent);

        BPMNRuntimeState finalState = simpleFire(transitionRelation, closeProcess, taskClosed);
        simpleAssertState(finalState, 0, 0);
        simpleAssertState(taskClosed, 1, 0);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);

        assertTrue(transitionRelation.fireableTransitionsFrom(finalState).isEmpty());
        simpleAssertState(finalState, 0, 0);
        simpleAssertState(taskClosed, 1, 0);
        simpleAssertState(taskActive, 0, 1);
        simpleAssertState(initialState, 1, 0);
    }

}