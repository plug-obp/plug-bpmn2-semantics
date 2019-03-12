package plug.bpmn2.interpretation.model.instance.impl;

import org.eclipse.bpmn2.Choreography;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.ChoreographyInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;

import java.util.HashSet;
import java.util.Set;

public class ChoreographyInstanceImpl
        extends InstanceBase<BPMNRuntimeInstance, Choreography>
        implements ChoreographyInstance {

    private final Set<Token> tokenSet;

    public ChoreographyInstanceImpl(BPMNRuntimeInstance parent, Choreography baseElement) {
        super(parent, baseElement);
        tokenSet = new HashSet<>();
    }

    @Override
    public Set<Token> getTokenSet() {
        return tokenSet;
    }

    @Override
    public void acceptInstanceVisitor(BPMNInstanceVisitor visitor) {
        visitor.visitChoreographyInstance(this);
    }

}
