package plug.bpmn2.tools.sandbox.transition;

import org.junit.Test;
import plug.bpmn2.tools.sandbox.BPMNLoader;
import plug.bpmn2.tools.sandbox.BPMNModelHandler;

public class RandomATSTest {

    @Test
    public void basicActivityTest() {
        BPMNLoader loader = new BPMNLoader();
        loader.loadModelFromURLString("omg.pizzaDelivery.bpmn");
        BPMNModelHandler handler = loader.getModelHandler();

    }

}
