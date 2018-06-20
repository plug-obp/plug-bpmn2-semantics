package plug.bpmn2.tools;

import plug.bpmn2.examples.simpleProcessInterpreter.SPITransitionFunction;
import plug.bpmn2.tools.BPMN2Loader;
import plug.bpmn2.tools.BPMN2TransitionRelation;
import plug.core.ILanguageLoader;
import plug.core.ILanguagePlugin;
import plug.core.IRuntimeView;

import java.net.URI;
import java.util.Map;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2Module implements ILanguagePlugin<BPMN2TransitionRelation> {

    @Override
    public String getName() {
        return "BPMN2";
    }

    @Override
    public String[] getExtensions() {
        return new String[] {".bpmn"};
    }

    @Override
    public ILanguageLoader<BPMN2TransitionRelation> getLoader() {
        return new ILanguageLoader<BPMN2TransitionRelation>() {
            @Override
            public BPMN2TransitionRelation getRuntime(URI uri, Map<String, Object> map) throws Exception {
                BPMN2Loader loader = new BPMN2Loader();
                loader.loadModelFromURL(uri.toURL());
                SPITransitionFunction stf = new SPITransitionFunction(loader.getModelObjectList().get(0));
                return new BPMN2TransitionRelation(stf);
            }
        };
    }

    @Override
    public IRuntimeView getRuntimeView(BPMN2TransitionRelation bpmn2TransitionRelation) {
        return null;
    }
}
