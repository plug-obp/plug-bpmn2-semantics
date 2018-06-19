package plug.bpmn2.examples.simpleProcessInterpreter.transitions;

import org.eclipse.bpmn2.FlowNode;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class FlowNodeTransition extends AbstractTransitionSimple {

    public FlowNodeTransition(FlowNode node) {
        super();
        getIncommingList().addAll(node.getIncoming());
        getNodeList().add(node);
        getOutgoingList().addAll(node.getOutgoing());
    }

}
