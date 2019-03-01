package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.FlowElementsContainer;

public interface FlowElementsContainerAction extends AtomicAction{

    @Override
    FlowElementsContainer scope();

}
