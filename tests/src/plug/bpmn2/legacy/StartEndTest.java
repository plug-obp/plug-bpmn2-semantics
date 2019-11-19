package plug.bpmn2.legacy;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import plug.bpmn2.legacy.semantics.BPMN2SystemConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StartEndTest extends BPMN2AbstractTest {

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
