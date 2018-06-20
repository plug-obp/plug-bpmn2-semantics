package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.tools.BPMN2Loader;

import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPIExampleMain {

    static public class SimpleExecutor {

        private final SPITransitionFunction transitionFunction;
        private SPISystemConfiguration currentConfiguration = null;

        public SimpleExecutor(SPITransitionFunction transitionFunction) {
            this.transitionFunction = transitionFunction;
            for (SPISystemConfiguration initialConfiguration : transitionFunction.getInitialConfigurations()) {
                if (currentConfiguration != null) {
                    System.err.println("Simple executor : multiple initial configurations detected.");
                }
                currentConfiguration = initialConfiguration;
            }
        }

        public SPISystemConfiguration getCurrentConfiguration() {
            return currentConfiguration;
        }

        public List<SPIAbstractTransition> getOutgoingTransitions() {
            return transitionFunction.getTransitionsFrom(currentConfiguration);
        }

        public boolean fireTransition(SPIAbstractTransition transition) {
            if (transition.evaluateGuard(currentConfiguration)) {
                SPISystemConfiguration target = transition.executeAction(currentConfiguration);
                if (target == null) {
                    System.err.println("Simple executor : could not execute transition ("
                            + transition + ") from given configuration (" + currentConfiguration + ")");
                    return false;
                }
                currentConfiguration = target;
                return true;
            }
            return false;
        }

    }

    static public void main(String[] args) {
        BPMN2Loader loader = new BPMN2Loader();
        //loader.loadModelFromURLString("plug/bpmn2/examples/process_1.bpmn");
        loader.loadModelFromURLString("plug/bpmn2/examples/triso - Order Process for Pizza V4.bpmn".replaceAll("\\_", "%20"));

        EObject modelRoot = loader.getModelObjectList().get(0);

        SPITransitionFunction transitionFunction = new SPITransitionFunction(modelRoot);

        System.out.println("All transitions :");
        for (SPIAbstractTransition transition : transitionFunction.getAllSystemTransitions()) {
            System.out.println("    " + transition);
        }
        System.out.println();

        System.out.println("Initial configurations :");
        for (SPISystemConfiguration initialConfiguration : transitionFunction.getInitialConfigurations()) {
            System.out.println("    " + initialConfiguration);
        }
        System.out.println();

        SimpleExecutor executor = new SimpleExecutor(transitionFunction);
        List<SPIAbstractTransition> outgoingTransitions = executor.getOutgoingTransitions();

        int max = 5;

        while (max >= 0 && !outgoingTransitions.isEmpty()) {
            System.out.println("Current configuration : " + executor.getCurrentConfiguration());
            System.out.println("Fire-able transitions :");
            for (SPIAbstractTransition transition : outgoingTransitions) {
                System.out.println("    " + transition);
            }
            System.out.println("Executing first one.");
            if (!executor.fireTransition(outgoingTransitions.get(0))) {
                System.err.println("Error while executing ...");
                break;
            }
            outgoingTransitions = executor.getOutgoingTransitions();
            System.out.println();
            max -= 1;
        }

        System.out.println("Final configuration : " + executor.getCurrentConfiguration());

    }

}
