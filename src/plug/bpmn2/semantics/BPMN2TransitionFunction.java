package plug.bpmn2.semantics;


import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.semantics.transition.BPMN2AbstractTransition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BPMN2TransitionFunction {

    public final BPMN2InitialSystemConfigurationsProvider initialConfigurationsProvider
            = new BPMN2InitialSystemConfigurationsProvider();

    private final BPMN2ToATS loader = new BPMN2ToATS();

    private Set<BPMN2SystemConfiguration> initialConfigurations;
    private BPMN2AbstractTransitionSystem transitionSystem;

    public BPMN2TransitionFunction(EObject modelRoot) {
        loadModel(modelRoot);
    }

    public void loadModel(EObject modelRoot) {
        initialConfigurations = initialConfigurationsProvider.getInitialSystemConfigurations(modelRoot);
        transitionSystem = loader.transformECoreModel(modelRoot);
    }

    public Set<BPMN2AbstractTransition> getAllSystemTransitions() {
        return transitionSystem.getTransitionSet();
    }

    public Set<BPMN2SystemConfiguration> getInitialConfigurations() {
        return initialConfigurations;
    }

    public List<BPMN2AbstractTransition> getTransitionsFrom(BPMN2SystemConfiguration source) {
        List<BPMN2AbstractTransition> result = new LinkedList<>();
        for (BPMN2AbstractTransition transition : transitionSystem.getTransitionSet()) {
            if (transition.evaluateGuard(source)) {
                result.add(transition);
            }
        }
        return result;
    }

    public List<BPMN2SystemConfiguration> fireTransition(BPMN2AbstractTransition transition, BPMN2SystemConfiguration source) {
        return Collections.singletonList(transition.executeAction(source));
    }


}
