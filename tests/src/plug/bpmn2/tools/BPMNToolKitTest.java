package plug.bpmn2.tools;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.module.BPMN2Loader;
import plug.bpmn2.tools.model.ParentMap;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class BPMNToolKitTest {

    private BPMNToolKit toolKit;

    private void load(String urlString, Consumer<String> logOutput) {
        BPMN2Loader loader = new BPMN2Loader();
        loader.loadModelFromURLString(urlString);
        Resource modelResource = loader.getModelResource();

        DocumentRootFetcher fetcher = new DocumentRootFetcher();
        DocumentRoot documentRoot = fetcher.getRoot(modelResource);

        toolKit = new BPMNToolKit();
        toolKit.setLogOutput(logOutput);

        toolKit.log(this, urlString, "Test run");

        toolKit.setDocumentRoot(documentRoot);
    }

    @Test
    public void omgPizzaDelivery() {
        load("omg.pizzaDelivery.bpmn", System.out::println);

        BPMNModelRuntimeState initialState = toolKit.getInitialState();

        ParentMap parentMap = toolKit.getParentMap();
        assertEquals("Hierarchy Depth", 3, parentMap.getHierarchyList().size());

        assertEquals("Message Flows", 6, initialState.getMessageFlowDataSet().size());
    }

    @Test
    public void enstabSpray() {
        load("enstab.spray.bpmn2", System.out::println);

        BPMNModelRuntimeState initialState = toolKit.getInitialState();

        ParentMap parentMap = toolKit.getParentMap();
        assertEquals("Hierarchy Depth", 3, parentMap.getHierarchyList().size());

        assertEquals("Message Flows", 6, initialState.getMessageFlowDataSet().size());
    }

    @Test
    public void mutex() {
        load("mutexProblemDescription.bpmn", System.out::println);

        BPMNModelRuntimeState initialState = toolKit.getInitialState();

        ParentMap parentMap = toolKit.getParentMap();
        assertEquals("Hierarchy Depth", 3, parentMap.getHierarchyList().size());

        assertEquals("Message Flows", 6, initialState.getMessageFlowDataSet().size());
    }

    private class DocumentRootFetcher extends Bpmn2Switch<DocumentRoot> {

        public DocumentRoot getRoot(Resource resource) {
            for (EObject object : resource.getContents()) {
                DocumentRoot documentRoot = doSwitch(object);
                if (documentRoot != null) {
                    return documentRoot;
                }
            }
            return null;
        }

        @Override
        public DocumentRoot caseDocumentRoot(DocumentRoot object) {
            return object;
        }
    }
}