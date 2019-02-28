package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Process;

public interface ProcessInstance extends FlowElementsContainerInstance {

    @Override
    Process baseElement();

}
