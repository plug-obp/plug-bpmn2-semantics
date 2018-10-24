package plug.bpmn2.semantics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParrallelGatewayTest extends BPMN2AbstractTest {

	@Override
	public String getFilePath() {

		return "resources/test/simple_parrallel_gateway.bpmn";

	}

	@Test
	public void test() {
		assertEquals(9, getKnownList().size());
		assertEquals(13, getNumberOfTransition());

	}

}
