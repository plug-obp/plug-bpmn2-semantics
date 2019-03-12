package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.FlowElementsContainer;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;

import java.util.Set;

public interface FlowElementsContainerInstance extends BPMNRuntimeInstance {

    @Override
    FlowElementsContainer getBaseElement();

    Set<Token> getTokenSet();

}
