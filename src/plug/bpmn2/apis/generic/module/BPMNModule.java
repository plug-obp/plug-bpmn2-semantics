package plug.bpmn2.apis.generic.module;

import plug.core.ILanguageLoader;
import plug.core.ILanguagePlugin;

public class BPMNModule implements ILanguagePlugin<BPMNTransitionRelation> {

    @Override
    public String getName() {
        return "BPMN2";
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".bpmn", ".bpmn2"};
    }

    @Override
    public ILanguageLoader<BPMNTransitionRelation> getLoader() {
        return new BPMNLanguageLoader();
    }

}
