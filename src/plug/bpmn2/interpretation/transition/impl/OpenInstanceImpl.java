package plug.bpmn2.interpretation.transition.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.transition.action.InstanceAction;

public class OpenInstanceImpl implements InstanceAction.Open {

    private final BaseElement newInstanceBaseElement;

    public OpenInstanceImpl(BaseElement newInstanceBaseElement) {
        this.newInstanceBaseElement = newInstanceBaseElement;
    }

    @Override
    public BaseElement getNewInstanceBaseElement() {
        return newInstanceBaseElement;
    }

}
