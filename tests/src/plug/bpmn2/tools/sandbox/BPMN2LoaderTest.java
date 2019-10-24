package plug.bpmn2.tools.sandbox;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.tools.sandbox.runtime.BPMN2InitialStateSetSupplier;
import plug.bpmn2.tools.sandbox.transition.BPMN2InstanceFactory;

import java.util.Set;

public class BPMN2LoaderTest {

    private final BPMN2Loader loader = new BPMN2Loader();
    private final BPMN2PrinterShort printer = new BPMN2PrinterShort();
    private final BPMN2ModelPrinter modelPrinter = new BPMN2ModelPrinter();

    private void printIssues(EObject object, int parentCount, String relationName) {
        if (parentCount == 0) {
            System.out.println("    Has no " + relationName + ": " + printer.getShortString(object));
        } else if (parentCount > 1) {
            System.out.println("    Has multiple " + relationName +  "s: " + printer.getShortString(object));
        }
    }

    private void load(String urlString) {
        System.out.println("Loading " + urlString);
        loader.loadModelFromURLString(urlString);
        ModelHandler modelHandler = loader.getModelHandler();

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

        System.out.println();
        Set<BPMNModelRuntimeState> initialStateSet = new BPMN2InitialStateSetSupplier().get(modelHandler);
        for (BPMNModelRuntimeState initialState : initialStateSet) {
            System.out.println(initialState);
        }
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

}
