package plug.bpmn2.examples;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import java.net.URL;
import java.util.List;

/**
 * Created by ciprian on 12/01/18.
 */
public class SimpleEvaluatorExample {
    public SimpleEvaluatorExample() {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put("bpmn", new Bpmn2ResourceFactoryImpl());
        EPackage.Registry.INSTANCE.put(Bpmn2Package.eNS_URI,
                Bpmn2Package.eINSTANCE);
    }

    public List<EObject> loadModel(String modelFilePath) {
        ResourceSetImpl rs = new ResourceSetImpl();
        Resource r = rs.getResource(URI.createURI(modelFilePath), true);

        return r.getContents();
    }

    public static void main(String args[]) {
        URL url = SimpleEvaluatorExample.class.getClassLoader().getResource("plug/bpmn2/examples/process_1.bpmn");
        SimpleEvaluatorExample m = new SimpleEvaluatorExample();

        SimpleEvaluator se = new SimpleEvaluator();
        for (EObject e : m.loadModel(url.toExternalForm())) {
            se.evaluate(e);
        }
    }
}
