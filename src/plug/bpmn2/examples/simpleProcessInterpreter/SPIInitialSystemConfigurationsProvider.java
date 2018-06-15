package plug.bpmn2.examples.simpleProcessInterpreter;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.*;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SPIInitialSystemConfigurationsProvider {

    public final Switch bpmn2switch = new Switch();

    public Set<SPISystemConfiguration> getInitialSystemConfigurations(EObject root) {
        List<EObject> initialTokens = bpmn2switch.doSwitch(root);
        SPISystemConfiguration result = new SPISystemConfiguration(initialTokens);
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
                for (EObject initialToken : doSwitch(flowElement)) {
                    result.add(initialToken);
                }
            }
            return result;
        }

        @Override
        public List<EObject> caseStartEvent(StartEvent object) {
            return Collections.singletonList(object);
        }

        @Override
        public List<EObject> defaultCase(EObject object) {
            return Collections.emptyList();
        }

    }

}


