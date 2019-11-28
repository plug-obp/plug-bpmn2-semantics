package plug.bpmn2.dsim.tools;

import org.junit.Test;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;
import plug.bpmn2.tools.BPMNLoader;
import plug.core.IFiredTransition;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class TransitionRelationTest {

    private void simpleAssertState(BPMNRuntimeState initialState, int tokens, int childs) {
        assertEquals(1, initialState.getRootInstances().size());
        for (BPMNRuntimeInstance instance : initialState.getRootInstances()) {
            assertTrue(instance instanceof ProcessInstance);
            ProcessInstance processInstance = (ProcessInstance) instance;
            assertEquals(tokens, processInstance.getTokenSet().size());
            assertEquals(childs, processInstance.getChildInstanceSet().size());
        }
    }

    @Test
    public void simple() {
        BPMNLoader loader = new BPMNLoader();
        loader.loadModelFromURLString("minimal/process_e0t0e1.bpmn");

        TransitionRelation transitionRelation = new TransitionRelation(loader.getModelHandler());

        Set<BPMNRuntimeState> initialStates = transitionRelation.initialConfigurations();
        assertEquals(1, initialStates.size());

        BPMNRuntimeState initialState = null;
        for (BPMNRuntimeState state : initialStates) {
            initialState = state;
        }
        simpleAssertState(initialState, 1, 0);

        Collection<Transition> fireableTransitions = transitionRelation.fireableTransitionsFrom(initialState);
        assertEquals(1, fireableTransitions.size());

        Transition transition = null;
        for (Transition oneTransition : fireableTransitions) {
            transition = oneTransition;
        }

        IFiredTransition<BPMNRuntimeState, ?> firedTransition = transitionRelation.fireOneTransition(initialState, transition);
        assertEquals(1, firedTransition.getTargets().size());

        BPMNRuntimeState target = firedTransition.getTarget(0);
        assertNotNull(target);
        simpleAssertState(target, 0, 1);

        simpleAssertState(initialState, 1, 0);
    }

}