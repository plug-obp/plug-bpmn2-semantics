package plug.bpmn2.interpretation.transition.action.definition;

import org.eclipse.bpmn2.BaseElement;

public interface InstanceAction extends ActionDefinition {

    BaseElement getInstanceBaseElement();

    interface Open extends InstanceAction {

    }

    interface Close extends InstanceAction {

    }

}
