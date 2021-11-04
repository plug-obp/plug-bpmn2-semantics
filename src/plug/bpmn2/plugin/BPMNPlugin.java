package plug.bpmn2.plugin;

import obp2.runtime.core.ILanguageModule;
import obp2.runtime.core.ILanguagePlugin;
import plug.bpmn2.model.BPMNLoader;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.transition.Transition;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.function.Function;

public class BPMNPlugin implements ILanguagePlugin<URI, BPMNRuntimeState, Transition, Void> {

    private BPMNLoader loader;
    private boolean fold = false;

    public BPMNPlugin() {
        loader = new BPMNLoader();
    }

    public boolean isFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    @Override
    public String getName() {
        return "BPMN4DSim";
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".bpmn", ".bpmn2"};
    }

    @Override
    public Function<URI, ILanguageModule<BPMNRuntimeState, Transition, Void>> languageModuleFunction() {
        return this::getRuntime;
    }

    public BPMNRuntime getRuntime(URI uri) {
        try {
            loader.loadModelFromURL(uri.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new BPMNRuntime(loader.getModelHandler(), isFold());
    }

    public BPMNRuntime getRuntime(String stringUri) {
        loader.loadModelFromURLString(stringUri);
        return new BPMNRuntime(loader.getModelHandler(), isFold());
    }

}
