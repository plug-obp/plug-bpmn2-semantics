package obp2.bpmn2.utils;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class BPMN2EmfUtils {

    static private boolean setupNeeded = true;

    public static void setupIfNeeded() {
        if (setupNeeded) {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn", new Bpmn2ResourceFactoryImpl());
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("bpmn2", new Bpmn2ResourceFactoryImpl());
            EPackage.Registry.INSTANCE.put(Bpmn2Package.eNS_URI, Bpmn2Package.eINSTANCE);
            setupNeeded = false;
        }
    }

    static public URI path2URI(String fileName) {
        return URI.createURI(fileName);
    }

    public static Resource getResource(URI resourceURI) {
        setupIfNeeded();
        ResourceSet resourceSet = new ResourceSetImpl();
        Resource resource = resourceSet.getResource(resourceURI, true);
        return resource;
    }

    public static Resource getResource(String fileName) {
        URI uri = path2URI(fileName);
        return getResource(uri);
    }

    public static DocumentRoot getDocumentRoot(Resource resource) {
        for (EObject object : resource.getContents()) {
            if (object instanceof DocumentRoot) return (DocumentRoot) object;
        }
        throw new IllegalArgumentException("Model doesn't have a DocumentRoot");
    }

    public static DocumentRoot getDocumentRoot(String fileName) {
        return getDocumentRoot(getResource(fileName));
    }

    public static DocumentRoot getDocumentRoot(URI resourceURI) {
        return getDocumentRoot(getResource(resourceURI));
    }

    public static DocumentRoot getDocumentRoot(java.net.URI resourceURI) {
        return getDocumentRoot(URI.createURI(resourceURI.toString()));
    }

    public static String getFlowElementName(FlowElement flowElement) {
        String name = flowElement.getName();
        if (name == null || name.isEmpty()) name = flowElement.getId();
        return name.replace("\n", " ");
    }

    public static String getContainerName(FlowElement flowElement) {
        FlowElementsContainer container = (FlowElementsContainer) flowElement.eContainer();
        if (container instanceof Process) return ((Process) container).getName();
        if (container instanceof SubProcess) return ((SubProcess) container).getName();
        return container.toString();
    }

}
