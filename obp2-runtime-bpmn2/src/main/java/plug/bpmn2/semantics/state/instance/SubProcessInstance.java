package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.SubProcess;

public interface SubProcessInstance extends
        ActivityInstance,
        FlowElementsContainerInstance {

    @Override
    SubProcess getBaseElement();

}
