package plug.bpmn2.tools.runtime.state;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.runtime.instance.BPMNInstanceFactory;

import java.util.Collections;
import java.util.Set;

public class BPMNInitialStateSetSupplier {

    private BPMNInstanceFactory instanceFactory = new BPMNInstanceFactory();

    public Set<BPMNRuntimeState> get(BPMNModelHandler modelHandler) {
        BPMNRuntimeState initialState = new BPMNRuntimeState();
        for (EObject rootInstanceElement : modelHandler.ownership.getFloorSet(0)) {
            if (instanceFactory.check(modelHandler, initialState, null, (BaseElement) rootInstanceElement)) {
                instanceFactory.create(modelHandler, initialState, null, (BaseElement) rootInstanceElement);
            }
        }
        return Collections.singleton(initialState);
    }

}
