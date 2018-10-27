package plug.bpmn2.semantics2.domains.instances;

import java.util.Collection;

public abstract class StructuralInstance {
    StructuralInstance parent;

    public void setParent(StructuralInstance parent) {
        this.parent = parent;
    }

    public abstract Collection<Object> getExecutableSteps(StructuralInstance instance);
}
