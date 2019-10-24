package plug.bpmn2.tools.sandbox.runtime;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.tools.sandbox.ModelHandler;
import plug.bpmn2.tools.sandbox.transition.BPMN2InstanceFactory;

import java.util.Collections;
import java.util.Set;

public class BPMN2InitialStateSetSupplier {

    private BPMN2InstanceFactory instanceSupplier = new BPMN2InstanceFactory();

    public Set<BPMNModelRuntimeState> get(ModelHandler modelHandler) {
        BPMNModelRuntimeState initialState = new BPMNModelRuntimeState();
        for (EObject rootInstanceElement : modelHandler.ownership.getFloorSet(0)) {
            if (instanceSupplier.check(modelHandler, initialState, null, (BaseElement) rootInstanceElement)) {
                instanceSupplier.create(modelHandler, initialState, null, (BaseElement) rootInstanceElement);
            }
        }
        return Collections.singleton(initialState);
    }

}
