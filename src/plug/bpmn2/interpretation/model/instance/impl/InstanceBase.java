package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

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
        if (o == null || getClass() != o.getClass()) return false;
        InstanceBase<?, ?> that = (InstanceBase<?, ?>) o;
        if (getBaseElement().equals(that.getBaseElement())) return false;
        // if (!getParent().equals(that.getParent())) return false;
        // TODO fix above overflow
        if (getChildInstanceList().size() != that.getChildInstanceList().size()) return false;
        if (!getChildInstanceList().containsAll(that.getChildInstanceList())) return false;
        if (!that.getChildInstanceList().containsAll(this.getChildInstanceList())) return false;
        // TODO the above will give false positive for {a, b, b} == {a, a, b} ... To strengthen.
        return true;
    }

    @Override
    public int hashCode() {
        // TODO safe but collision heavy hash, make it better
        return Objects.hash(getBaseElement());
    }

}
