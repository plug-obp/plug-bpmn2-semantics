package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

abstract class InstanceBase<P extends BPMNRuntimeInstance, E extends BaseElement> implements BPMNRuntimeInstance {

    private final P parent;
    private final E baseElement;
    private final Set<BPMNRuntimeInstance> childInstanceSet;

    InstanceBase(P parent, E baseElement) {
        this.parent = parent;
        this.baseElement = baseElement;
        childInstanceSet = new HashSet<>();
    }

    @Override
    public P getParent() {
        return parent;
    }

    @Override
    public Set<BPMNRuntimeInstance> getChildInstanceSet() {
        return childInstanceSet;
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
                getChildInstanceSet().equals(that.getChildInstanceSet());
    }

    @Override
    public int hashCode() {
        // TODO
        // Can not include parent in hashcode, as it tries to hash his children too ...
        // return Objects.hash(getParent(), getBaseElement(), getChildInstanceSet());
        return Objects.hash(getBaseElement(), getChildInstanceSet());
    }

}
