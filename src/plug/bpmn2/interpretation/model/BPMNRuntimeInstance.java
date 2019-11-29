package plug.bpmn2.interpretation.model;

import java.util.List;

public interface BPMNRuntimeInstance extends BPMNRuntimeBaseElement {

    BPMNRuntimeInstance getParent();

    List<BPMNRuntimeInstance> getChildInstanceList();

    void acceptInstanceVisitor(BPMNInstanceVisitor visitor);

}
