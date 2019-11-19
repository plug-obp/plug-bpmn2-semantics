package plug.bpmn2.legacy;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import plug.bpmn2.legacy.semantics.BPMN2SystemConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExclusiveGatewayTest extends BPMN2AbstractTest {

	@Override
	public String getFilePath() {
		return "resources/tests/simple_exclusive_gateway.bpmn";
	}

	@Test
	public void test() {
		assertEquals(5, getKnownList().size());
		assertEquals(6, getNumberOfTransition());
		assertFirst();
		assertSecond();
		assertThird();
		assertFourth();
		assertFith();

	}

	private void assertFirst() {
		BPMN2SystemConfiguration configuration = getKnownList().get(0);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Start Event 1", "Exclusive Gateway 2");
	}

	private void assertSecond() {
		BPMN2SystemConfiguration configuration = getKnownList().get(1);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Exclusive Gateway 2", "End Event 1");

	}

	private void assertThird() {
		BPMN2SystemConfiguration configuration = getKnownList().get(2);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Exclusive Gateway 2", "End Event 1");

	}

	private void assertFourth() {
		BPMN2SystemConfiguration configuration = getKnownList().get(3);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(1, tokens.size());
		EObject token = tokens.get(0);
		assertSequenceFlow(token, "Exclusive Gateway 2", "End Event 1");

	}

	private void assertFith() {
		BPMN2SystemConfiguration configuration = getKnownList().get(4);
		List<EObject> tokens = configuration.getTokens();
		assertEquals(0, tokens.size());
	}

}
