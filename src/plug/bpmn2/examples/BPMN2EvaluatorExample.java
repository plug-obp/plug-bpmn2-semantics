package plug.bpmn2.examples;

import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.module.BPMN2Loader;
import plug.bpmn2.semantics.BPMN2SystemConfiguration;
import plug.bpmn2.semantics.BPMN2TransitionFunction;
import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;

import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2EvaluatorExample {

    static public class SimpleExecutor {

        private final BPMN2TransitionFunction transitionFunction;
        private BPMN2SystemConfiguration currentConfiguration = null;

        public SimpleExecutor(BPMN2TransitionFunction transitionFunction) {
            this.transitionFunction = transitionFunction;
            for (BPMN2SystemConfiguration initialConfiguration : transitionFunction.getInitialConfigurations()) {
                if (currentConfiguration != null) {
                    System.err.println("Simple executor : multiple initial configurations detected.");
                }
                currentConfiguration = initialConfiguration;
            }
        }

        public BPMN2SystemConfiguration getCurrentConfiguration() {
            return currentConfiguration;
        }

        public List<BPMN2AbstractTransition> getOutgoingTransitions() {
            return transitionFunction.getTransitionsFrom(currentConfiguration);
        }

        public boolean fireTransition(BPMN2AbstractTransition transition) {
            if (transition.evaluateGuard(currentConfiguration)) {
                BPMN2SystemConfiguration target = transition.executeAction(currentConfiguration);
                if (target == null) {
                    System.err.println("Simple executor : could not interpretation transition ("
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

        BPMN2TransitionFunction transitionFunction = new BPMN2TransitionFunction(modelRoot);

        System.out.println("All transitions :");
        for (BPMN2AbstractTransition transition : transitionFunction.getAllSystemTransitions()) {
            System.out.println("    " + transition);
        }
        System.out.println();

        System.out.println("Initial configurations :");
        for (BPMN2SystemConfiguration initialConfiguration : transitionFunction.getInitialConfigurations()) {
            System.out.println("    " + initialConfiguration);
        }
        System.out.println();

        SimpleExecutor executor = new SimpleExecutor(transitionFunction);
        List<BPMN2AbstractTransition> outgoingTransitions = executor.getOutgoingTransitions();

        int max = 5;

        while (max >= 0 && !outgoingTransitions.isEmpty()) {
            System.out.println("Current configuration : " + executor.getCurrentConfiguration());
            System.out.println("Fire-able transitions :");
            for (BPMN2AbstractTransition transition : outgoingTransitions) {
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
