package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.Choreography;

public interface ChoreographyInstance extends CollaborationInstance, FlowElementsContainerInstance {

    @Override
    Choreography getBaseElement();

}
