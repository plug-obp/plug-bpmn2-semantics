package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.SequenceFlow;

import java.util.Set;

public interface ChangeTokensAction extends FlowElementsContainerAction {

    Set<SequenceFlow> sequenceFlowsToRemove();
    Set<SequenceFlow> sequenceFlowsToAdd();

    // TODO Specify incoming and outgoing arity

}
