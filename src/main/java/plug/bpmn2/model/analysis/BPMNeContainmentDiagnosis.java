package plug.bpmn2.model.analysis;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.Set;

public class BPMNeContainmentDiagnosis extends AbstractDAGDiagnosis<EObject> {

    @Override
    protected void fillNodeTargetSourceSets(EObject rootObject) {
        new InternalSwitch().doSwitch(rootObject);
    }

    class InternalSwitch extends Bpmn2Switch<Object> {

        private final Object NON_NULL_TAG = 0;

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            doSwitch(object.getDefinitions());
            return NON_NULL_TAG;
        }

        @Override
        public Object caseDefinitions(Definitions object) {
            for (RootElement rootElement : object.getRootElements()) {
                doSwitch(rootElement);
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseBaseElement(BaseElement object) {
            if (getNodeSet().add(object)) {
                Set<EObject> content = getTargetSet(object);
                content.addAll(object.eContents());
                for (EObject contained : content) {
                    getSourceSet(contained).add(object);
                    doSwitch(contained);
                }
            }
            return NON_NULL_TAG;
        }

    }
}
