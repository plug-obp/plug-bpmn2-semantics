package plug.bpmn2.dsim.module;

import plug.bpmn2.dsim.tools.TransitionRelation;
import plug.bpmn2.tools.BPMNLoader;
import plug.core.ILanguageLoader;
import plug.core.ILanguagePlugin;

public class BPMNPlugin implements ILanguagePlugin<TransitionRelation> {


    @Override
    public String getName() {
        return "BPMN4DSim";
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".bpmn", ".bpmn2"};
    }

    @Override
    public ILanguageLoader<TransitionRelation> getLoader() {
        return (uri, map) -> {
            BPMNLoader loader = new BPMNLoader();
            loader.loadModelFromURL(uri.toURL());
            return new TransitionRelation(loader.getModelHandler());
        };
    }

}
