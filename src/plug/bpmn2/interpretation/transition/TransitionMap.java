package plug.bpmn2.interpretation.transition;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.transition.action.ActionDefinition;

import java.util.Map;
import java.util.Set;

public interface TransitionMap {

    Map<BaseElement, Set<ActionDefinition>> getTransitionMap();

}
