package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.HashSet;
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

}