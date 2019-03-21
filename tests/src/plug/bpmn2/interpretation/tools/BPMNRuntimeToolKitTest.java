package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.module.BPMN2Loader;

public class BPMNRuntimeToolKitTest {

    @Test
    public void getInitialState() {
        // String urlString = "plug/bpmn2/examples/process_1.bpmn";
        String urlString = "plug/bpmn2/examples/triso - Order Process for Pizza V4.bpmn".replaceAll("\\_", "%20");

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