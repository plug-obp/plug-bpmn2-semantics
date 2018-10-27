package plug.bpmn2.semantics2;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.semantics2.domains.instances.ModelInstance;

import java.util.Collection;
import java.util.Set;

public class BPMN2Semantics {

    public ModelInstance initial(DocumentRoot root) {
        BPMN2InitialConfigurationProvider provider = new BPMN2InitialConfigurationProvider();
        return provider.getTopLevelInstance(root);
    }

    Collection<Object> getExecutableSteps(ModelInstance modelInstance) {
        return modelInstance.getExecutableSteps(modelInstance);
    }
}
