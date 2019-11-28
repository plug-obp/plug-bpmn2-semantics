package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.*;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.HashSet;
import java.util.Set;

public class TransitionsFireable {

    static public void addRelated(BPMNModelHandler model,
                                  BPMNRuntimeState state,
                                  BPMNRuntimeInstance instance,
                                  Set<Transition> targetCollection) {
        Transition.Close close = new Transition.Close(model, instance);
        if (close.guard(state)) {
            targetCollection.add(close);
        }
        for (EObject childObject : model.ownership.getTargetSet(instance.getBaseElement())) {
            if (childObject instanceof Activity || childObject instanceof FlowElementsContainer) {
                BaseElement childElement = (BaseElement) childObject;
                Transition.Open open = new Transition.Open(model, instance, childElement);
                if (open.guard(state)) {
                    targetCollection.add(open);
                }
            }
        }
        for (BPMNRuntimeInstance childInstance : instance.getChildInstanceSet()) {
            addRelated(model, state, childInstance, targetCollection);
        }
    }

    static public Set<Transition> get(BPMNModelHandler model, BPMNRuntimeState state) {
        HashSet<Transition> result = new HashSet<>();
        for (BPMNRuntimeInstance instance : state.getRootInstances()) {
            addRelated(model, state, instance, result);
        }
        return result;
    }

}
