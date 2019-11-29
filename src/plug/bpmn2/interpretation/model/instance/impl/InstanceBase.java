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
        // TODO fix that overflow
        return //Objects.equals(getParent(), that.getParent()) &&
                getBaseElement().equals(that.getBaseElement()) &&
                getChildInstanceList().equals(that.getChildInstanceList());
    }

    @Override
    public int hashCode() {
        // TODO
        // Can not include parent in hashcode, as it tries to hash his children too ...
        // return Objects.hash(getParent(), getBaseElement(), getChildInstanceList());
        return Objects.hash(getBaseElement(), getChildInstanceList());
    }

}
