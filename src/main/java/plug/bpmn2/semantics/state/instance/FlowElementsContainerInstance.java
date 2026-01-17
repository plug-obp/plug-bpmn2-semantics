package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.FlowElementsContainer;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.instance.data.Token;

import java.util.Set;

public interface FlowElementsContainerInstance extends BPMNRuntimeInstance {

    @Override
    FlowElementsContainer getBaseElement();

    Set<Token> getTokenSet();

}
