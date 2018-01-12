package plug.bpmn2.semantics;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import plug.bpmn2.semantics.evaluators.SimpleEvaluator;
//import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by ciprian on 12/01/18.
 */
public class Main {
    public Main() {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put("bpmn", new Bpmn2ResourceFactoryImpl());
        EPackage.Registry.INSTANCE.put(Bpmn2Package.eNS_URI,
                Bpmn2Package.eINSTANCE);
    }

    public List<EObject> loadModel(File modelFile) {
        return loadModel(modelFile.getAbsolutePath());
    }

    public List<EObject> loadModel(String modelFilePath) {
        ResourceSetImpl rs = new ResourceSetImpl();
        Resource r = rs.getResource(URI.createFileURI(modelFilePath), true);

        return r.getContents();
    }

    public static void main(String args[]) {
        URL url = Main.class.getClassLoader().getResource("plug/bpmn2/examples/process_1.bpmn");
        Main m = new Main();

        SimpleEvaluator se = new SimpleEvaluator();
        for (EObject e : m.loadModel(url.getFile())) {
            se.evaluate(e);
        }
    }
}
