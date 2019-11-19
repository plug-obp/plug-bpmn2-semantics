package plug.bpmn2.tools.common;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import plug.bpmn2.tools.BPMNLoader;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.common.printer.BPMNModelPrinter;
import plug.bpmn2.tools.common.printer.BPMNPrinterShort;

public class BPMNLoaderTest {

    private final BPMNLoader loader = new BPMNLoader();
    private final BPMNPrinterShort printer = new BPMNPrinterShort();
    private final BPMNModelPrinter modelPrinter = new BPMNModelPrinter();

    private void printIssues(EObject object, int parentCount, String relationName) {
        if (parentCount == 0) {
            System.out.println("    Has no " + relationName + ": " + printer.getShortString(object));
        } else if (parentCount > 1) {
            System.out.println("    Has multiple " + relationName + "s: " + printer.getShortString(object));
        }
    }

    private void load(String urlString) {
        System.out.println("Loading " + urlString);
        loader.loadModelFromURLString(urlString);
        BPMNModelHandler modelHandler = loader.getModelHandler();

        System.out.println();
        System.out.println("Model Elements:");
        for (EObject object : modelHandler.containment.getNodeSet()) {
            System.out.println("    " + printer.getShortString(object));
        }

        System.out.println();
        System.out.println("Instantiable Elements:");
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


    @Test
    public void omgPizzaDelivery() {
        load("omg.pizzaDelivery.bpmn");
    }

    @Test
    public void enstabSpray() {
        load("enstab.spray.bpmn2");
    }

    @Test
    public void mutex() {
        load("mutexProblemDescription.bpmn");
    }

    @Test
    public void process_e0t0e1() {
        load("minimal/process_e0t0e1.bpmn");
    }

    @Test
    public void process_CAS_191029() {
        load("CAS/CAS_191029.bpmn");
    }

    @Test
    public void process_CAS_191030() {
        load("CAS/CAS_191030.bpmn");
    }

}
