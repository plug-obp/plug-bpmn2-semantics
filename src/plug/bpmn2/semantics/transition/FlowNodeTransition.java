package plug.bpmn2.semantics.transition;

import org.eclipse.bpmn2.FlowNode;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class FlowNodeTransition extends AbstractTransitionSimple {

    public FlowNodeTransition(FlowNode node) {
        super();
        getIncomingList().addAll(node.getIncoming());
        getNodeList().add(node);
        getOutgoingList().addAll(node.getOutgoing());
    }

}
