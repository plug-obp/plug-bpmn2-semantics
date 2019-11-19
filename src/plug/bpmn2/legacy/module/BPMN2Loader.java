package plug.bpmn2.legacy.module;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import plug.bpmn2.legacy.semantics.BPMN2TransitionFunction;
import plug.core.ILanguageLoader;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2Loader implements ILanguageLoader<BPMN2TransitionRelation> {

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

    public List<EObject> getModelObjectList() {
        return modelResource.getContents();
    }

    @Override
    public BPMN2TransitionRelation getRuntime(java.net.URI uri, Map<String, Object> map) throws Exception {
        loadModelFromURL(uri.toURL());
        BPMN2TransitionFunction modelTransitionRelation = new BPMN2TransitionFunction(getModelObjectList().get(0));
        return new BPMN2TransitionRelation(modelTransitionRelation);
    }

}
