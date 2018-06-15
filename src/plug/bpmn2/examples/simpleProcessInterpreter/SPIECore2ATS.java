package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPIECore2ATS {

    public final Loader loader = new Loader();

    public SPIAbstractTransitionSystem transformECoreModel(EObject root) {
        SPIAbstractTransitionSystem transitionSystem = new SPIAbstractTransitionSystem();
        for (SPIAbstractTransition transition : loader.doSwitch(root)) {
            transitionSystem.getTransitionSet().add(transition);
            transitionSystem.getInitiatorSet().addAll(transition.getInitiatorSet());
        }
        return transitionSystem;
    }


    public class Loader extends Bpmn2Switch<List<SPIAbstractTransition>> {

        @Override
        public List<SPIAbstractTransition> caseDocumentRoot(DocumentRoot object) {
            return doSwitch(object.getDefinitions());
        }

        @Override
        public List<SPIAbstractTransition> caseDefinitions(Definitions object) {
            List<SPIAbstractTransition> result = new LinkedList<>();
            for (RootElement element : object.getRootElements()) {
                result.addAll(doSwitch(element));
            }
            return result;
        }

        @Override
        public List<SPIAbstractTransition> caseProcess(Process object) {
            List<SPIAbstractTransition> result = new LinkedList<>();
            for (FlowElement element : object.getFlowElements()) {
                result.addAll(doSwitch(element));
            }
            return result;
        }

        public SPIAbstractTransition buildTransition(EObject tokenSource, EObject tokenTarget) {
            Predicate<SPISystemConfiguration> guard = c -> c.getTokens().contains(tokenSource);
            Function<SPISystemConfiguration, SPISystemConfiguration> action = c -> {
                SPISystemConfiguration target = new SPISystemConfiguration(c.getTokens());
                target.getTokens().remove(tokenSource);
                if (tokenTarget != null) {
                    target.getTokens().add(tokenTarget);
                }
                return target;
            };
            SPIAbstractTransition transition =  SPIAbstractTransition.base(guard, action);
            return transition;
        }

        @Override
        public List<SPIAbstractTransition> caseStartEvent(StartEvent object) {
            List<SPIAbstractTransition> result = new LinkedList<>();
            for (SequenceFlow flow :object.getOutgoing()) {
                EObject target = flow.getTargetRef();
                SPIAbstractTransition transition = buildTransition(object, target);
                transition.getInitiatorSet().add(object);
                transition.getInitiatorSet().add(flow);
                result.add(transition);
            }
            return result;
        }

        @Override
        public List<SPIAbstractTransition> defaultCase(EObject object) {
            return Collections.emptyList();
        }
    }

}
