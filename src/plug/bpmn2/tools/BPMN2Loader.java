package plug.bpmn2.tools;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.EObject;
import java.net.URL;

import java.util.List;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2Loader {

    static private boolean setupNeeded = true;

    public static void setupIfNeeded() {
        if (setupNeeded) {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn", new Bpmn2ResourceFactoryImpl());
            EPackage.Registry.INSTANCE.put(Bpmn2Package.eNS_URI, Bpmn2Package.eINSTANCE);
            setupNeeded = false;
        }
    }

    private String modelFilePath;
    private Resource modelResource;

    public void loadModelFromURLString(String urlString) {
        loadModelFromURL(BPMN2Loader.class.getClassLoader().getResource(urlString));
    }

    public void loadModelFromURL(URL modelURL) {
        loadModelFromFilePath(modelURL.toExternalForm());
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

    public List<EObject> getModelObjectList() {
        return modelResource.getContents();
    }

}
