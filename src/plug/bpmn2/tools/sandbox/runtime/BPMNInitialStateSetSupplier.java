package plug.bpmn2.tools.sandbox.runtime;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.tools.sandbox.BPMNModelHandler;
import plug.bpmn2.tools.sandbox.instance.BPMNInstanceFactory;

import java.util.Collections;
import java.util.Set;

public class BPMNInitialStateSetSupplier {

    private BPMNInstanceFactory instanceFactory = new BPMNInstanceFactory();

    public Set<BPMNModelRuntimeState> get(BPMNModelHandler modelHandler) {
        BPMNModelRuntimeState initialState = new BPMNModelRuntimeState();
        for (EObject rootInstanceElement : modelHandler.ownership.getFloorSet(0)) {
            if (instanceFactory.check(modelHandler, initialState, null, (BaseElement) rootInstanceElement)) {
                instanceFactory.create(modelHandler, initialState, null, (BaseElement) rootInstanceElement);
            }
        }
        return Collections.singleton(initialState);
    }

}
