package plug.bpmn2.dsim.module;

import plug.bpmn2.tools.AbstractTest;

import java.net.URL;

import static org.junit.Assert.fail;

public class BPMNPluginTest extends AbstractTest {

    @Override
    protected void load(String urlString) {
        BPMNPlugin plugin = new BPMNPlugin();
        try {
            URL modelURL = BPMNPluginTest.class.getClassLoader().getResource(urlString);
            plugin.getLoader().getRuntime(modelURL.toURI());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Override
    protected void testModel() {

    }

}