package plug.bpmn2.dsim.tools.utils;

import org.eclipse.bpmn2.*;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.dsim.tools.Transition;
import plug.bpmn2.dsim.tools.transitions.TransitionCatchEvent;
import plug.bpmn2.dsim.tools.transitions.TransitionClose;
import plug.bpmn2.dsim.tools.transitions.TransitionEndEvent;
import plug.bpmn2.dsim.tools.transitions.TransitionOpen;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.HashSet;
import java.util.Set;

public class TransitionsFireable {

    static public void addFlowNodeTransitions(BPMNModelHandler model,
                                              BPMNRuntimeState state,
                                              FlowElementsContainerInstance flowInstance,
                                              FlowNode flowNode,
                                              Set<Transition> targetCollection) {
        if (flowNode instanceof EndEvent) {
            EndEvent endEvent = (EndEvent) flowNode;
            targetCollection.add(new TransitionEndEvent(
                    model, state, flowInstance, endEvent
            ));
        } else if (flowNode instanceof IntermediateCatchEvent) {
            IntermediateCatchEvent catchEvent = (IntermediateCatchEvent) flowNode;
            TransitionCatchEvent transition = new TransitionCatchEvent(
                    model, state, flowInstance, catchEvent
            );
            if (transition.guard(state)) {
                targetCollection.add(transition);
            }
        }

    }

    static public void addFlowElementsContainerTransitions(BPMNModelHandler model,
                                                           BPMNRuntimeState state,
                                                           FlowElementsContainerInstance flowInstance,
                                                           Set<Transition> targetCollection) {
        for (Token token : flowInstance.getTokenSet()) {
            FlowNode target = token.getBaseElement().getTargetRef();
            addFlowNodeTransitions(model, state, flowInstance, target, targetCollection);
        }
        for (FlowElement flowElement : flowInstance.getBaseElement().getFlowElements()) {
            if (flowElement instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) flowElement;
                addFlowNodeTransitions(model, state, flowInstance, startEvent, targetCollection);
            }
        }
        // IntermediateCatchEvent

    }

    static public void addOpenChildInstanceTransitions(BPMNModelHandler model,
                                                       BPMNRuntimeState state,
                                                       BPMNRuntimeInstance instance,
                                                       Set<Transition> targetCollection) {
        for (EObject childObject : model.ownership.getTargetSet(instance.getBaseElement())) {
            if (childObject instanceof Activity || childObject instanceof FlowElementsContainer) {
                BaseElement childElement = (BaseElement) childObject;
                TransitionOpen open = new TransitionOpen(model, state, instance, childElement);
                if (open.guard(state)) {
                    targetCollection.add(open);
                }
            }
        }
    }

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
            addFlowElementsContainerTransitions(model, state, flowInstance, targetCollection);
        }
        addOpenChildInstanceTransitions(model, state, instance, targetCollection);
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
