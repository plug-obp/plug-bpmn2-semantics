package plug.bpmn2.semantics2;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import java.net.URL;

public class BPMN2Loader {

    static private boolean setupNeeded = setupIfNeeded();

    private static boolean setupIfNeeded() {
        if (setupNeeded) {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn", new Bpmn2ResourceFactoryImpl());
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn2", new Bpmn2ResourceFactoryImpl());
            EPackage.Registry.INSTANCE.put(Bpmn2Package.eNS_URI, Bpmn2Package.eINSTANCE);
            return false;
        }
        return true;
    }

    URL modelURL;
    String modelURLString;
    String modelFilePath;

    public void setModelURL(URL modelURL) {
        this.modelURL = modelURL;
        modelURLString = null;
        modelFilePath = null;
    }

    public void setModelURL(String modelURL) {
        this.modelURLString = modelURL;
        this.modelURL = null;
        this.modelFilePath = null;
    }

    public void setModelFilePath(String modelFilePath) {
        this.modelFilePath = modelFilePath;
        this.modelURL = null;
        this.modelURLString = null;
    }

    DocumentRoot loadModelFromURL(String urlString) {
        URL url = BPMN2Loader.class.getClassLoader().getResource(urlString);
        return loadModelFromURL(url);
    }

    DocumentRoot loadModelFromURL(URL modelURL) {
        return loadModelFromFilePath(modelURL.toExternalForm());
    }

    DocumentRoot loadModelFromFilePath(String modelFilePath) {
        plug.bpmn2.module.BPMN2Loader.setupIfNeeded();
        ResourceSetImpl rs = new ResourceSetImpl();
        Resource modelResource = rs.getResource(URI.createURI(modelFilePath), true);
        return (DocumentRoot)modelResource.getContents().get(0);
    }

    public DocumentRoot load() {
        if (modelURL != null) {
            return loadModelFromURL(modelURL);
        }
        if (modelFilePath != null) {
            return loadModelFromFilePath(modelFilePath);
        }
        if (modelURLString != null) {
            return loadModelFromURL(modelURLString);
        }
        return null;
    }
}
