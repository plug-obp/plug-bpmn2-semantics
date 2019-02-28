package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Collaboration;
import plug.bpmn2.interpretation.model.instance.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.InstanceVisitor;
import plug.bpmn2.interpretation.model.instance.CollaborationInstance;

public class CollaborationInstanceImpl
        extends InstanceBase<BPMNRuntimeInstance, Collaboration>
        implements CollaborationInstance {

    public CollaborationInstanceImpl(BPMNRuntimeInstance parent, Collaboration baseElement) {
        super(parent, baseElement);
    }

    @Override
    public void acceptInstanceVisitor(InstanceVisitor visitor) {
        visitor.visitCollaborationInstance(this);
    }

}
