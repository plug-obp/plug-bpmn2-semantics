package plug.bpmn2.semantics.state.instance.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.utils.BPMNRuntimeEquals;
import plug.bpmn2.semantics.state.utils.BPMNRuntimeHashCode;

import java.util.LinkedList;
import java.util.List;

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
