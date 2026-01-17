package plug.bpmn2.semantics.state;

import java.util.List;

public interface BPMNRuntimeInstance extends BPMNRuntimeBaseElement {

    BPMNRuntimeInstance getParent();

    List<BPMNRuntimeInstance> getChildInstanceList();

    void acceptInstanceVisitor(BPMNInstanceVisitor visitor);

}
