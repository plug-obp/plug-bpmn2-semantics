package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.FlowElementsContainer;
import plug.bpmn2.interpretation.model.BPMNRuntimeToken;

import java.util.Set;

public interface FlowElementsContainerInstance extends BPMNRuntimeInstance {

    @Override
    FlowElementsContainer baseElement();

    Set<BPMNRuntimeToken> tokenSet();

}
