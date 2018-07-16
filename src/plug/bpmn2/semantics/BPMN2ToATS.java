package plug.bpmn2.semantics;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.semantics.transition.FlowNodeTransition;
import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2ToATS {

    public final Loader loader = new Loader();

    public BPMN2AbstractTransitionSystem transformECoreModel(EObject root) {
        BPMN2AbstractTransitionSystem transitionSystem = new BPMN2AbstractTransitionSystem();
        for (BPMN2AbstractTransition transition : loader.doSwitch(root)) {
            transitionSystem.getTransitionSet().add(transition);
            transitionSystem.getInitiatorSet().addAll(transition.getIncomingList());
        }
        return transitionSystem;
    }


    public class Loader extends Bpmn2Switch<List<BPMN2AbstractTransition>> {

        @Override
        public List<BPMN2AbstractTransition> caseDocumentRoot(DocumentRoot object) {
            return doSwitch(object.getDefinitions());
        }

        @Override
        public List<BPMN2AbstractTransition> caseDefinitions(Definitions object) {
            List<BPMN2AbstractTransition> result = new LinkedList<>();
            for (RootElement element : object.getRootElements()) {
                result.addAll(doSwitch(element));
            }
            return result;
        }

        @Override
        public List<BPMN2AbstractTransition> caseProcess(Process object) {
            List<BPMN2AbstractTransition> result = new LinkedList<>();
            for (FlowElement element : object.getFlowElements()) {
                result.addAll(doSwitch(element));
            }
            return result;
        }

        @Override
        public List<BPMN2AbstractTransition> caseFlowNode(FlowNode object) {
            return Collections.singletonList(new FlowNodeTransition(object));
        }

        @Override
        public List<BPMN2AbstractTransition> caseStartEvent(StartEvent object) {
            return Collections.emptyList();
        }

        @Override
        public List<BPMN2AbstractTransition> defaultCase(EObject object) {
            return Collections.emptyList();
        }
    }

}
