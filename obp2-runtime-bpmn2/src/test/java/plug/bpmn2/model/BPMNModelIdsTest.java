package plug.bpmn2.model;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.AbstractTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BPMNModelIdsTest extends AbstractTest {

    @Override
    protected void testModel() {
        BPMNLoader loader = getLoader();

        Set<String> idSet = new HashSet<>();
        Set<String> nullIdSet = new HashSet<>();
        for (EObject object : loader.getModelHandler().containment.getNodeSet()) {
            if (object instanceof BaseElement) {
                BaseElement baseElement = (BaseElement) object;
                String id = loader.getModelHandler().idsProvider.get(baseElement);
                if (baseElement.getId() == null) {
                    nullIdSet.add(id);
                }
                if (!idSet.add(id)) {
                    fail("Duplicate Id:" + id);
                }
            }
        }
        if (!nullIdSet.isEmpty()) {
            System.out.println("Ids attributed to BaseElements with a null base id:");
            for (String nullId : nullIdSet) {
                System.out.println("    " + nullId);
            }
        } else {
            System.out.println("No null base id were found (that's good!).");
        }
    }
    
}