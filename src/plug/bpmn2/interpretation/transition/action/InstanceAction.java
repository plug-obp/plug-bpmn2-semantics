package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.BaseElement;

public interface InstanceAction extends ActionDefinition {

    interface Open extends InstanceAction {

        BaseElement getNewInstanceBaseElement();

        @Override
        default void accept(Visitor visitor) {
            visitor.visitOpenInstance(this);
        }

    }

    interface Close extends InstanceAction {

        @Override
        default void accept(Visitor visitor) {
            visitor.visitCloseInstance(this);
        }

    }

}
