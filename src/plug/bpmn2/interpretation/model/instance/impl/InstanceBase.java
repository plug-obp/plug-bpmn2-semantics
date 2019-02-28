package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.instance.BPMNRuntimeInstance;

import java.util.HashSet;
import java.util.Set;

abstract class InstanceBase<P extends BPMNRuntimeInstance, E extends BaseElement> implements BPMNRuntimeInstance {

    private final P parent;
    private final E baseElement;
    private final Set<BPMNRuntimeInstance> childInstanceSet;

    public InstanceBase(P parent, E baseElement) {
        this.parent = parent;
        this.baseElement = baseElement;
        childInstanceSet = new HashSet<>();
        parent.childInstanceSet().add(this);
    }

    @Override
    public P parent() {
        return parent;
    }

    @Override
    public Set<BPMNRuntimeInstance> childInstanceSet() {
        return childInstanceSet;
    }

    @Override
    public E baseElement() {
        return baseElement;
    }

}
