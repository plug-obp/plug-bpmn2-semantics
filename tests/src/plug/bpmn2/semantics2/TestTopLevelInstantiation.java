package plug.bpmn2.semantics2;

import org.eclipse.bpmn2.DocumentRoot;
import org.junit.Test;
import plug.bpmn2.semantics2.domains.instances.ModelInstance;

public class TestTopLevelInstantiation {

    @Test
    public void testLoadProcess1() {
        BPMN2Loader loader = new BPMN2Loader();
        loader.setModelFilePath("resources/tests/process_1.bpmn");
        DocumentRoot root = loader.load();

        BPMN2InitialConfigurationProvider initialConfigurationProvider = new BPMN2InitialConfigurationProvider();
        ModelInstance instance = initialConfigurationProvider.getTopLevelInstance(root);

        assert(instance != null);
    }
}
