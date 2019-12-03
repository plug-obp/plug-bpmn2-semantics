package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.utils.BPMNRuntimeEquals;
import plug.bpmn2.interpretation.model.utils.BPMNRuntimeHashCode;

import java.util.*;

abstract class InstanceBase<P extends BPMNRuntimeInstance, E extends BaseElement> implements BPMNRuntimeInstance {

    private final P parent;
    private final E baseElement;
    private final List<BPMNRuntimeInstance> childInstanceList;

    InstanceBase(P parent, E baseElement) {
        this.parent = parent;
        this.baseElement = baseElement;
        childInstanceList = new LinkedList<>();
    }

    @Override
    public P getParent() {
        return parent;
    }

    @Override
    public List<BPMNRuntimeInstance> getChildInstanceList() {
        return childInstanceList;
    }

    @Override
    public E getBaseElement() {
        return baseElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BPMNRuntimeInstance)) return false;
        BPMNRuntimeInstance that = (BPMNRuntimeInstance) o;
        return BPMNRuntimeEquals.instanceEquals(this, that);
    }

    @Override
    public int hashCode() {
        return BPMNRuntimeHashCode.hashInstance(this);
    }

}
