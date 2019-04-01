package plug.bpmn2.semantics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParrallelGatewayTest extends BPMN2AbstractTest {

	@Override
	public String getFilePath() {

		return "resources/tests/simple_parrallel_gateway.bpmn";

	}

	@Test
	public void test() {
		assertEquals(9, getKnownList().size());
		assertEquals(13, getNumberOfTransition());

	}

}
