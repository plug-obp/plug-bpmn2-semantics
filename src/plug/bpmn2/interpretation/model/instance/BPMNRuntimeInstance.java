package plug.bpmn2.interpretation.model.instance;

import plug.bpmn2.interpretation.model.BPMNRuntimeBaseElement;

import java.util.Set;

public interface BPMNRuntimeInstance extends BPMNRuntimeBaseElement {

    BPMNRuntimeInstance parent();

    Set<BPMNRuntimeInstance> childInstanceSet();

    void acceptInstanceVisitor(InstanceVisitor visitor);

}
