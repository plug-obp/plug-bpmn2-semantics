package plug.bpmn2.interpretation.model;

import java.util.Set;

public interface BPMNRuntimeInstance extends BPMNRuntimeBaseElement {

    BPMNRuntimeInstance getParent();

    Set<BPMNRuntimeInstance> getChildInstanceSet();

    void acceptInstanceVisitor(BPMNInstanceVisitor visitor);

}
