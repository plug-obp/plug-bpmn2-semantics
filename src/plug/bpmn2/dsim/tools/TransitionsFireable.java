package plug.bpmn2.dsim.tools;

import org.eclipse.bpmn2.*;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.HashSet;
import java.util.Set;

public class TransitionsFireable {

    static public void addRelated(BPMNModelHandler model,
                                  BPMNRuntimeState state,
                                  BPMNRuntimeInstance instance,
                                  Set<Transition> targetCollection) {
        TransitionClose close = new TransitionClose(model, state, instance);
        if (close.guard(state)) {
            targetCollection.add(close);
        }
        if (instance instanceof FlowElementsContainerInstance) {
            FlowElementsContainerInstance flowInstance = (FlowElementsContainerInstance) instance;
            for (Token token : flowInstance.getTokenSet()) {
                FlowNode target = token.getBaseElement().getTargetRef();
                if (target instanceof EndEvent) {
                    EndEvent endEvent = (EndEvent) target;
                    targetCollection.add(new TransitionEndEvent(
                            model, state, instance, endEvent
                    ));
                }
            }
        }
        for (EObject childObject : model.ownership.getTargetSet(instance.getBaseElement())) {
            if (childObject instanceof Activity || childObject instanceof FlowElementsContainer) {
                BaseElement childElement = (BaseElement) childObject;
                TransitionOpen open = new TransitionOpen(model, state, instance, childElement);
                if (open.guard(state)) {
                    targetCollection.add(open);
                }
            }
        }
        for (BPMNRuntimeInstance childInstance : instance.getChildInstanceList()) {
            addRelated(model, state, childInstance, targetCollection);
        }
    }

    static public Set<Transition> get(BPMNModelHandler model, BPMNRuntimeState state) {
        HashSet<Transition> result = new HashSet<>();
        for (BPMNRuntimeInstance instance : state.getRootInstanceList()) {
            addRelated(model, state, instance, result);
        }
        return result;
    }

}
