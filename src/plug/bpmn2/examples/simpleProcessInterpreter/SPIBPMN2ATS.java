package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.examples.simpleProcessInterpreter.transitions.FlowNodeTransition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPIBPMN2ATS {

    public final Loader loader = new Loader();

    public SPIAbstractTransitionSystem transformECoreModel(EObject root) {
        SPIAbstractTransitionSystem transitionSystem = new SPIAbstractTransitionSystem();
        for (SPIAbstractTransition transition : loader.doSwitch(root)) {
            transitionSystem.getTransitionSet().add(transition);
            transitionSystem.getInitiatorSet().addAll(transition.getIncommingList());
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

        @Override
        public List<SPIAbstractTransition> caseFlowNode(FlowNode object) {
            return Collections.singletonList(new FlowNodeTransition(object));
        }

        @Override
        public List<SPIAbstractTransition> caseStartEvent(StartEvent object) {
            return Collections.emptyList();
        }

        @Override
        public List<SPIAbstractTransition> defaultCase(EObject object) {
            return Collections.emptyList();
        }
    }

}
