package plug.bpmn2.legacy.semantics;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2InitialSystemConfigurationsProvider {

    public final Switch bpmn2switch = new Switch();

    public Set<BPMN2SystemConfiguration> getInitialSystemConfigurations(EObject root) {
        List<EObject> initialTokens = bpmn2switch.doSwitch(root);
        BPMN2SystemConfiguration result = new BPMN2SystemConfiguration(initialTokens);
        result.canonize();
        return Collections.singleton(result);
    }

    public class Switch extends Bpmn2Switch<List<EObject>> {
        @Override
        public List<EObject> caseDocumentRoot(DocumentRoot object) {
            return doSwitch(object.getDefinitions());
        }

        @Override
        public List<EObject> caseDefinitions(Definitions object) {
            List<EObject> result = new LinkedList<>();
            for (EObject modelNode : object.getRootElements()) {
                for (EObject initialToken : doSwitch(modelNode)) {
                    result.add(initialToken);
                }
            }
            return result;
        }

        @Override
        public List<EObject> caseProcess(Process object) {
            List<EObject> result = new LinkedList<>();
            for (EObject flowElement : object.getFlowElements()) {
                result.addAll(doSwitch(flowElement));
            }
            return result;
        }

        @Override
        public List<EObject> caseStartEvent(StartEvent object) {
            List<EObject> result = new LinkedList<>();
            result.addAll(object.getOutgoing());
            return result;
        }

        @Override
        public List<EObject> defaultCase(EObject object) {
            return Collections.emptyList();
        }

    }

}


