package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Choreography;
import plug.bpmn2.interpretation.model.instance.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeToken;
import plug.bpmn2.interpretation.model.instance.InstanceVisitor;
import plug.bpmn2.interpretation.model.instance.ChoreographyInstance;

import java.util.HashSet;
import java.util.Set;

public class ChoreographyInstanceImpl
        extends InstanceBase<BPMNRuntimeInstance, Choreography>
        implements ChoreographyInstance {

    private final Set<BPMNRuntimeToken> tokenSet;

    public ChoreographyInstanceImpl(BPMNRuntimeInstance parent, Choreography baseElement) {
        super(parent, baseElement);
        tokenSet = new HashSet<>();
    }

    @Override
    public Set<BPMNRuntimeToken> tokenSet() {
        return tokenSet;
    }

    @Override
    public void acceptInstanceVisitor(InstanceVisitor visitor) {
        visitor.visitChoreographyInstance(this);
    }

}
