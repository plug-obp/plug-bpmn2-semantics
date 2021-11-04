package plug.bpmn2.semantics.state.instance.impl;

import org.eclipse.bpmn2.Collaboration;
import plug.bpmn2.semantics.state.BPMNInstanceVisitor;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.instance.CollaborationInstance;

public class CollaborationInstanceImpl
        extends InstanceBase<BPMNRuntimeInstance, Collaboration>
        implements CollaborationInstance {

    public CollaborationInstanceImpl(BPMNRuntimeInstance parent, Collaboration baseElement) {
        super(parent, baseElement);
    }

    @Override
    public void acceptInstanceVisitor(BPMNInstanceVisitor visitor) {
        visitor.visitCollaborationInstance(this);
    }

}
