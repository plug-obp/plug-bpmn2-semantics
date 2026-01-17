package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.Collaboration;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;

public interface CollaborationInstance extends BPMNRuntimeInstance {

    @Override
    Collaboration getBaseElement();

}
