package plug.bpmn2.examples.simpleProcessInterpreter;


import org.eclipse.emf.ecore.EObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SPITransitionFunction {

    public final SPIInitialSystemConfigurationsProvider initialConfigurationsProvider
            = new SPIInitialSystemConfigurationsProvider();

    private final SPIECore2ATS loader = new SPIECore2ATS();

    private Set<SPISystemConfiguration> initialConfigurations;
    private SPIAbstractTransitionSystem transitionSystem;

    public SPITransitionFunction(EObject modelRoot) {
        loadModel(modelRoot);
    }

    public void loadModel(EObject modelRoot) {
        initialConfigurations = initialConfigurationsProvider.getInitialSystemConfigurations(modelRoot);
        transitionSystem = loader.transformECoreModel(modelRoot);
    }

    public Set<SPISystemConfiguration> getInitialConfigurations() {
        return initialConfigurations;
    }

    public List<SPIAbstractTransition> getTransitionsFrom(SPISystemConfiguration source) {
        List<SPIAbstractTransition> result = new LinkedList<>();
        for (SPIAbstractTransition transition : transitionSystem.getTransitionSet()) {
            if (transition.evaluateGuard(source)) {
                result.add(transition);
            }
        }
        return result;
    }

    public List<SPISystemConfiguration> fireTransition(SPIAbstractTransition transition, SPISystemConfiguration source) {
        return Collections.singletonList(transition.executeAction(source));
    }


}
