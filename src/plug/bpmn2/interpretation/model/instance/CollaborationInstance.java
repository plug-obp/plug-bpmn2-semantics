package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Collaboration;

public interface CollaborationInstance extends BPMNRuntimeInstance {

    @Override
    Collaboration baseElement();

}
