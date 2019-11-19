package plug.bpmn2.legacy.examples;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.legacy.module.BPMN2Loader;
import plug.bpmn2.tools.common.printer.BPMNPrinterShort;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMNLoadNPrintExample {

    static public void main(String[] args) {
        BPMN2Loader loader = new BPMN2Loader();
        loader.loadModelFromURLString("plug/bpmn2/examples/process_1.bpmn");

        System.out.println("Model file path : " + loader.getModelFilePath());
        System.out.println("Model elements : ");

        BPMNPrinterShort printer = new BPMNPrinterShort();

        for (EObject e : loader.getModelObjectList()) {
            System.out.println("    " + printer.getShortString(e));
        }
    }

}
