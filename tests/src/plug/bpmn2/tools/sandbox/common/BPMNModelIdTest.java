package plug.bpmn2.tools.sandbox.common;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import plug.bpmn2.tools.sandbox.BPMNLoader;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BPMNModelIdTest {

    private final BPMNLoader loader = new BPMNLoader();
    private final BPMNPrinterShort printer = new BPMNPrinterShort();

    private void load(String urlString) {
        loader.loadModelFromURLString(urlString);
        assertUniqueIds();
    }

    private void assertUniqueIds() {
        Set<String> idSet = new HashSet<>();
        Set<String> nullIdSet = new HashSet<>();
        for (EObject object : loader.getModelHandler().containment.getNodeSet()) {
            if (object instanceof BaseElement) {
                BaseElement baseElement = (BaseElement) object;
                String id = loader.getModelHandler().id.get(baseElement);
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

    @Test
    public void omgPizzaDelivery() {
        load("omg.pizzaDelivery.bpmn");
    }

    @Test
    public void enstabSpray() {
        load("enstab.spray.bpmn2");
    }

    @Test
    public void mutex() {
        load("mutexProblemDescription.bpmn");
    }

    @Test
    public void minimal() {
        load("minimal/process_e0t0e1.bpmn");
    }

    @Test
    public void process_CAS_191029() {
        load("CAS/CAS_191029.bpmn");
    }


}