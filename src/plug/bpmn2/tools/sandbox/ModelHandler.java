package plug.bpmn2.tools.sandbox;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.tools.sandbox.preprocessing.BPMN2InstanceOwnerDiagnosis;
import plug.bpmn2.tools.sandbox.preprocessing.BPMN2eContainmentDiagnosis;
import plug.bpmn2.tools.sandbox.transition.BPMN2InstanceFactory;

public class ModelHandler {

    public final BPMN2eContainmentDiagnosis containment = new BPMN2eContainmentDiagnosis();
    public final BPMN2InstanceOwnerDiagnosis ownership = new BPMN2InstanceOwnerDiagnosis();
    public final BPMN2InstanceFactory instance = new BPMN2InstanceFactory();

    public ModelHandler(DocumentRoot documentRoot) {
        containment.populate(documentRoot);
        ownership.populate(documentRoot);
    }

}
