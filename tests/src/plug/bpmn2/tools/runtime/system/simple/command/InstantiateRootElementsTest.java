package plug.bpmn2.tools.runtime.system.simple.command;

import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.AbstractTest;
import plug.bpmn2.tools.BPMNLoader;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.common.printer.BPMNModelPrinter;
import plug.bpmn2.tools.common.printer.BPMNPrinterShort;

public class InstantiateRootElementsTest extends AbstractTest {

    @Override
    protected void testModel() {
        BPMNLoader loader = getLoader();
        BPMNModelHandler modelHandler = getModelHandler();
        BPMNPrinterShort printer = getPrinter();
        BPMNModelPrinter modelPrinter = getModelPrinter();

        InstantiateRootElements instantiateRootElementsa = new InstantiateRootElements(modelHandler);

        BPMNRuntimeState state = new BPMNRuntimeState();
        instantiateRootElementsa.execute(state, null);

        System.out.println(state);
    }

}