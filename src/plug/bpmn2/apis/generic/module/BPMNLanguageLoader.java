package plug.bpmn2.apis.generic.module;

import plug.bpmn2.tools.BPMNLoader;
import plug.core.ILanguageLoader;

import java.net.URI;
import java.util.Map;

public class BPMNLanguageLoader implements ILanguageLoader<BPMNTransitionRelation> {

    private BPMNLoader loader = new BPMNLoader();

    @Override
    public BPMNTransitionRelation getRuntime(URI uri, Map<String, Object> map) throws Exception {
        loader.loadModelFromURL(uri.toURL());
        return new BPMNTransitionRelation(loader.getModelHandler());
    }

}
