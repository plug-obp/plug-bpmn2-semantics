package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.Process;

public interface ProcessInstance extends FlowElementsContainerInstance {

    @Override
    Process getBaseElement();

}
