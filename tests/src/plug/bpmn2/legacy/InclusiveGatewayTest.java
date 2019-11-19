package plug.bpmn2.legacy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InclusiveGatewayTest extends BPMN2AbstractTest {

	@Override
	public String getFilePath() {
		return "resources/tests/simple_inclusive_gateway.bpmn";
	}

	@Test
	public void test() {
		assertEquals(9, getKnownList().size());
		assertEquals(19, getNumberOfTransition());

	}

}
