package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.tools.base.ParentMap;
import plug.bpmn2.module.BPMN2Loader;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class BPMNRuntimeToolKitTest {

    private BPMNRuntimeToolKit toolKit;

    private void load(String urlString, Consumer<String> logOutput) {
        BPMN2Loader loader = new BPMN2Loader();
        loader.loadModelFromURLString(urlString);
        Resource modelResource = loader.getModelResource();

        DocumentRootFetcher fetcher = new DocumentRootFetcher();
        DocumentRoot documentRoot = fetcher.getRoot(modelResource);

        toolKit = new BPMNRuntimeToolKit();
        toolKit.setLogOutput(logOutput);
        toolKit.setDocumentRoot(documentRoot);

        toolKit.println(this, urlString, "Test run");
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
    public void enstabSprayBpmn2() {
        load("enstab.spray.bpmn2", System.out::println);

        BPMNModelRuntimeState initialState = toolKit.getInitialState();

        ParentMap parentMap = toolKit.getParentMap();
        assertEquals("Hierarchy Depth", 3, parentMap.getHierarchyList().size());

        assertEquals("Message Flows", 6, initialState.getMessageFlowDataSet().size());
    }

    @Test
    public void getInitialState() {
        // String urlString = "plug/bpmn2/examples/process_1.bpmn";
        // String urlString = "plug/bpmn2/examples/omg.pizzaDelivery.bpmn".replaceAll("\\_", "%20");
        // String urlString = "plug/bpmn2/examples/enstab.spray.bpmn2";
        //String urlString = "omg.pizzaDelivery.bpmn".replaceAll("\\_", "%20");
        String urlString = "enstab.spray.bpmn2";

        BPMN2Loader loader = new BPMN2Loader();
        loader.loadModelFromURLString(urlString);
        Resource modelResource = loader.getModelResource();

        DocumentRootFetcher fetcher = new DocumentRootFetcher();
        DocumentRoot documentRoot = fetcher.getRoot(modelResource);

        BPMNRuntimeToolKit toolKit = new BPMNRuntimeToolKit();
        toolKit.setLogOutput(System.out::println);
        toolKit.setDocumentRoot(documentRoot);

        BPMNModelRuntimeState initialState = toolKit.getInitialState();
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