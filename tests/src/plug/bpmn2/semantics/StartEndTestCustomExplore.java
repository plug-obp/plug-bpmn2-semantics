package plug.bpmn2.semantics;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;

public class StartEndTestCustomExplore extends BPMN2AbstractTest {

	@Override
	public void explore() {
		knownList.addAll(transitionFunction.getInitialConfigurations());
		BPMN2SystemConfiguration initialConfiguration = knownList.get(0);
		BPMN2AbstractTransition transition = transitionFunction.getTransitionsFrom(initialConfiguration).get(0);
		knownList.add(transition.executeAction(initialConfiguration));
	}

	@Override
	public String getFilePath() {
		return "resources/tests/process_1.bpmn";
	}

	@Test
	public void test() {
		assertEquals(2, getKnownList().size());
		assertFirst();
		assertSecond();
	}

	private void assertFirst() {
		BPMN2SystemConfiguration configuration = getKnownList().get(0);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Start Event 1", "End Event 1");
	}

	private void assertSecond() {
		BPMN2SystemConfiguration configuration = getKnownList().get(1);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(0, tokens.size());
	}

}
