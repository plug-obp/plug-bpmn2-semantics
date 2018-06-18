package plug.bpmn2.examples.simpleProcessInterpreter.transitions;

import org.eclipse.bpmn2.SequenceFlow;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class SequenceFlowTransition extends AbstractTransitionSimple {

    public SequenceFlowTransition(SequenceFlow flow) {
        super();
        getSourceList().add(flow.getSourceRef());
        getMediumList().add(flow);
        getTargetList().add(flow.getTargetRef());
    }

}
