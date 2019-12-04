package plug.bpmn2.interpretation.model.instance.impl;


import org.eclipse.bpmn2.FlowElementsContainer;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;

import java.util.HashSet;
import java.util.Set;

abstract class FlowElementContainerInstanceBase<T extends FlowElementsContainer>
        extends InstanceBase<BPMNRuntimeInstance, T>
        implements FlowElementsContainerInstance {

    private final Set<Token> tokenSet;

    public FlowElementContainerInstanceBase(BPMNRuntimeInstance parent, T baseElement) {
        super(parent, baseElement);
        this.tokenSet = new HashSet<>();
    }

    @Override
    public Set<Token> getTokenSet() {
        return tokenSet;
    }

}
