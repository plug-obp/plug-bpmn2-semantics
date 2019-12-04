package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Choreography;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.ChoreographyInstance;

public class ChoreographyInstanceImpl
        extends FlowElementContainerInstanceBase<Choreography>
        implements ChoreographyInstance {

    public ChoreographyInstanceImpl(BPMNRuntimeInstance parent, Choreography baseElement) {
        super(parent, baseElement);
    }

    @Override
    public void acceptInstanceVisitor(BPMNInstanceVisitor visitor) {
        visitor.visitChoreographyInstance(this);
    }

}
