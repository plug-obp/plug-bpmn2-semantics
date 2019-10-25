package plug.bpmn2.tools.sandbox;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.tools.sandbox.preprocessing.dag.BPMNInstanceOwnerDiagnosis;
import plug.bpmn2.tools.sandbox.preprocessing.dag.BPMNeContainmentDiagnosis;
import plug.bpmn2.tools.sandbox.transition.BPMNInstanceFactory;

public class BPMNModelHandler {

    public final BPMNeContainmentDiagnosis containment = new BPMNeContainmentDiagnosis();
    public final BPMNInstanceOwnerDiagnosis ownership = new BPMNInstanceOwnerDiagnosis();
    public final BPMNInstanceFactory instance = new BPMNInstanceFactory();

    public BPMNModelHandler(DocumentRoot documentRoot) {
        containment.populate(documentRoot);
        ownership.populate(documentRoot);
    }

}
