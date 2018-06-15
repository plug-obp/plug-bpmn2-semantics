package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.tools.BPMN2Loader;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPIExampleMain {

    static public void main(String[] args) {
        BPMN2Loader loader = new BPMN2Loader();
        loader.loadModelFromURLString("plug/bpmn2/examples/process_1.bpmn");

        EObject modelRoot = loader.getModelObjectList().get(0);

        SPITransitionFunction transitionFunction = new SPITransitionFunction(modelRoot);

        for (SPISystemConfiguration initialConfiguration : transitionFunction.getInitialConfigurations()) {
            System.out.println("Initial configuration : " + initialConfiguration);
            for (SPIAbstractTransition transition : transitionFunction.getTransitionsFrom(initialConfiguration)) {
                System.out.println("    " + transition + " -> " + transition.executeAction(initialConfiguration));
            }
        }

        // TODO

    }

}
