package plug.bpmn2.tools.sandbox;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import plug.bpmn2.tools.sandbox.diagnosis.BPMN2ContainmentDiagnosis;

public class BPMN2LoaderTest {

    private final BPMN2Loader loader = new BPMN2Loader();
    private final BPMN2PrinterShort printer = new BPMN2PrinterShort();
    private final BPMN2ModelPrinter modelPrinter = new BPMN2ModelPrinter();

    private void load(String urlString) {
        System.out.println("Loading " + urlString);
        System.out.println();
        loader.loadModelFromURLString(urlString);
        BPMN2ContainmentDiagnosis references = new BPMN2ContainmentDiagnosis();
        references.populate(loader.getDocumentRoot());
        System.out.println("Model Objects");
        for (EObject object : references.getModelObjectSet()) {
            System.out.println("    " + printer.getShortString(object));
        }
        System.out.println();
        System.out.println("Diagram Objects");
        for (EObject object : references.getViewObjectSet()) {
            System.out.println("    " + printer.getShortString(object));
        }
        System.out.println();
        System.out.println("Is that a problem?");
        for (EObject object : references.getModelObjectSet()) {
            int numberOfContainers = references.getContainers(object).size();
            if (numberOfContainers == 0) {
                System.out.println("    Has no container: " + printer.getShortString(object));
            } else if (numberOfContainers > 1) {
                System.out.println("    Has multiple containers: " + printer.getShortString(object));
                for (EObject container : references.getContainers(object)) {
                    System.out.println("        " + printer.getShortString(object));
                }
            }
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

}
