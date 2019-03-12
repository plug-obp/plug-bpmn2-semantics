package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Collaboration;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

public interface CollaborationInstance extends BPMNRuntimeInstance {

    @Override
    Collaboration getBaseElement();

}
