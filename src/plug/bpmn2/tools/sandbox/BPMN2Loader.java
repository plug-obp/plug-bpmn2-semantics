package plug.bpmn2.tools.sandbox;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import java.net.URL;

public class BPMN2Loader {

    static private boolean setupNeeded = true;

    public static void setupIfNeeded() {
        if (setupNeeded) {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn", new Bpmn2ResourceFactoryImpl());
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn2", new Bpmn2ResourceFactoryImpl());
            EPackage.Registry.INSTANCE.put(Bpmn2Package.eNS_URI, Bpmn2Package.eINSTANCE);
            setupNeeded = false;
        }
    }

    private String modelFilePath;
    private Resource modelResource;

    public void loadModelFromURLString(String urlString) {
        URL modelURL = BPMN2Loader.class.getClassLoader().getResource(urlString);
        loadModelFromURL(modelURL);
    }

    public void loadModelFromURL(URL modelURL) {
        String filePath = modelURL.toExternalForm();
        loadModelFromFilePath(filePath);
    }

    public void loadModelFromFilePath(String modelFilePath) {
        this.modelFilePath = modelFilePath;
        BPMN2Loader.setupIfNeeded();
        ResourceSetImpl rs = new ResourceSetImpl();
        modelResource = rs.getResource(URI.createURI(getModelFilePath()), true);
    }

    public String getModelFilePath() {
        return modelFilePath;
    }

    public Resource getModelResource() {
        return modelResource;
    }

    public DocumentRoot getDocumentRoot() {
        return new DocumentRootFetcher().getRoot(modelResource);
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
