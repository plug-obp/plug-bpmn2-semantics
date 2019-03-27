package plug.bpmn2.interpretation.transition.action.definition;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.transition.action.BPMNRuntimeAction;

public interface ActionDefinition extends BPMNRuntimeAction {

    BaseElement getBaseElementScope();

}
