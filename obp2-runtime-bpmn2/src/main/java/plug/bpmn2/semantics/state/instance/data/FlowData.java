package plug.bpmn2.semantics.state.instance.data;

import plug.bpmn2.semantics.state.BPMNRuntimeBaseElement;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;

public interface FlowData extends BPMNRuntimeBaseElement {

    BPMNRuntimeInstance getSourceParent();
    BPMNRuntimeInstance getTargetParent();

    Object getData();

}
