package plug.bpmn2.module;

import plug.core.ILanguageLoader;
import plug.core.ILanguagePlugin;
import plug.core.IRuntimeView;

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
        return new String[] {".bpmn", ".bpmn2"};
    }

    @Override
    public ILanguageLoader<BPMN2TransitionRelation> getLoader() {
        return new BPMN2Loader();
    }

    @Override
    public IRuntimeView getRuntimeView(BPMN2TransitionRelation bpmn2TransitionRelation) {
        return null;
    }

}
