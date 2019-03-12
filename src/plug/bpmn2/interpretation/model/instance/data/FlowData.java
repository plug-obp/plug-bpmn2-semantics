package plug.bpmn2.interpretation.model.instance.data;

import plug.bpmn2.interpretation.model.BPMNRuntimeBaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

public interface FlowData extends BPMNRuntimeBaseElement {

    BPMNRuntimeInstance getSourceParent();
    BPMNRuntimeInstance getTargetParent();

    Object getData();

}
