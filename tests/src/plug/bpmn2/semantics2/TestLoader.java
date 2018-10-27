package plug.bpmn2.semantics2;

import org.eclipse.bpmn2.DocumentRoot;
import org.junit.Test;

public class TestLoader {
    @Test
    public void testLoadProcess1() {
        BPMN2Loader loader = new BPMN2Loader();
        loader.setModelFilePath("resources/tests/process_1.bpmn");
        DocumentRoot root = loader.load();

        assert(root.getDefinitions().getRootElements().size() == 1);
    }

}
