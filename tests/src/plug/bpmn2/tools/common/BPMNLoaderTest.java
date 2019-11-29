package plug.bpmn2.tools.common;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.AbstractTest;
import plug.bpmn2.tools.BPMNLoader;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.common.printer.BPMNModelPrinter;
import plug.bpmn2.tools.common.printer.BPMNPrinterShort;

public class BPMNLoaderTest extends AbstractTest {

    private void printIssues(EObject object, int parentCount, String relationName) {
        BPMNPrinterShort printer = getPrinter();

        if (parentCount == 0) {
            System.out.println("    Has no " + relationName + ": " + printer.getShortString(object));
        } else if (parentCount > 1) {
            System.out.println("    Has multiple " + relationName + "s: " + printer.getShortString(object));
        }
    }

    @Override
    protected void testModel() {
        BPMNLoader loader = getLoader();
        BPMNModelHandler modelHandler = getModelHandler();
        BPMNPrinterShort printer = getPrinter();
        BPMNModelPrinter modelPrinter = getModelPrinter();

        System.out.println();
        System.out.println("Model ElementsCommon:");
        for (EObject object : modelHandler.containment.getNodeSet()) {
            System.out.println("    " + printer.getShortString(object));
        }

        System.out.println();
        System.out.println("Instantiable ElementsCommon:");
        for (EObject object : modelHandler.ownership.getNodeSet()) {
            System.out.println("    " + printer.getShortString(object));
        }

        System.out.println();
        System.out.println("No or multiple eContainers:");
        for (EObject object : modelHandler.containment.getNodeSet()) {
            int numberOfContainers = modelHandler.containment.getSourceSet(object).size();
            printIssues(object, numberOfContainers, "eContainer");
        }

        System.out.println();
        System.out.println("No or multiple instance owners:");
        for (EObject object : modelHandler.ownership.getNodeSet()) {
            int numberOfOwner = modelHandler.ownership.getSourceSet(object).size();
            printIssues(object, numberOfOwner, "instance owner");
        }

        System.out.println();
        System.out.println(modelPrinter.getString(loader.getDocumentRoot()));

    }

}
