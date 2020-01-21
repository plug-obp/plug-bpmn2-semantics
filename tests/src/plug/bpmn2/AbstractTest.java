package plug.bpmn2;

import org.junit.Test;
import plug.bpmn2.tools.BPMNLoader;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.common.printer.BPMNModelPrinter;
import plug.bpmn2.tools.common.printer.BPMNPrinterShort;

public abstract class AbstractTest {

    private final BPMNLoader loader = new BPMNLoader();
    private BPMNModelHandler modelHandler;
    private final BPMNPrinterShort printer = new BPMNPrinterShort();
    private final BPMNModelPrinter modelPrinter = new BPMNModelPrinter();

    protected BPMNLoader getLoader() {
        return loader;
    }

    protected BPMNModelHandler getModelHandler() {
        return modelHandler;
    }

    protected BPMNPrinterShort getPrinter() {
        return printer;
    }

    protected BPMNModelPrinter getModelPrinter() {
        return modelPrinter;
    }

    protected void load(String urlString) {
        System.out.println("Loading " + urlString);
        getLoader().loadModelFromURLString(urlString);
        modelHandler = getLoader().getModelHandler();
    }

    protected abstract void testModel();

    @Test
    public void omgPizzaDelivery() {
        load("omg.pizzaDelivery.bpmn");
        testModel();
    }

    @Test
    public void enstabSpray() {
        load("enstab.spray.bpmn2");
        testModel();
    }

    @Test
    public void mutex() {
        load("mutexProblemDescription.bpmn");
        testModel();
    }

    @Test
    public void process_e0t0e1() {
        load("minimal/process_e0t0e1.bpmn");
        testModel();
    }

    @Test
    public void process_2() {
        load("minimal/process_2.bpmn");
        testModel();
    }

    @Test
    public void process_CAS_191029() {
        load("CAS/CAS_191029.bpmn");
        testModel();
    }

    @Test
    public void process_CAS_191030() {
        load("CAS/CAS_191030.bpmn");
        testModel();
    }

    @Test
    public void process_CAS_191128() {
        load("CAS/CAS_191128.bpmn");
        testModel();
    }

    @Test
    public void messages_1to1() {
        load("minimal/messages_1to1.bpmn");
        testModel();
    }

    @Test
    public void parallel_gateway() {
        load("minimal/parallel_gateway.bpmn");
        testModel();
    }

}
