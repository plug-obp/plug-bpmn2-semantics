package plug.bpmn2.examples.simpleProcessInterpreter.transitions;

import org.eclipse.bpmn2.EndEvent;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class EndEventTransition extends AbstractTransitionSimple {

    public EndEventTransition(EndEvent endEvent) {
        super();
        getSourceList().add(endEvent);
    }

}
