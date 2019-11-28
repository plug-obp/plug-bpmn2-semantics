package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Process;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collections;
import java.util.Set;

class TransitionsInitial {

    static public Set<BPMNRuntimeState> get(BPMNModelHandler model) {
        BPMNRuntimeState initialState = new BPMNRuntimeState();
        for (EObject rootObject : model.ownership.getFloorSet(0)) {
            if (rootObject instanceof Collaboration || rootObject instanceof Process) {
                BaseElement rootElement = (BaseElement) rootObject;
                ElementsOpen.open(model, initialState, null, rootElement, true);
            }
        }
        return Collections.singleton(initialState);
    }

}
