package plug.bpmn2.semantics;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StartTaskEndTest extends BPMN2AbstractTest {

	@Override
	public String getFilePath() {
		return "resources/tests/process_simpleTask.bpmn";
	}

	@Test
	public void test() {
		assertEquals(3, getKnownList().size());
		assertEquals(2, getNumberOfTransition());
		assertFirst();
		assertSecond();
		assertThird();
	}

	private void assertFirst() {
		BPMN2SystemConfiguration configuration = getKnownList().get(0);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Start Event 1", "Task 1");
	}

	private void assertSecond() {
		BPMN2SystemConfiguration configuration = getKnownList().get(1);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Task 1", "End Event 1");
	}

	private void assertThird() {
		BPMN2SystemConfiguration configuration = getKnownList().get(2);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(0, tokens.size());
	}

}