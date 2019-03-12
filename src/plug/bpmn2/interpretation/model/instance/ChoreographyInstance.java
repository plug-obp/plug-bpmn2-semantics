package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Choreography;

public interface ChoreographyInstance extends CollaborationInstance, FlowElementsContainerInstance {

    @Override
    Choreography getBaseElement();

}
