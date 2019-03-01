package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.FlowElement;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;

public class TokensInitializer {

    private final TokenPool tokenPool;

    public TokensInitializer(TokenPool tokenPool) {
        this.tokenPool = tokenPool;
    }

    public void initialize(FlowElementsContainerInstance instance) {
        for (FlowElement flowElement : instance.baseElement().getFlowElements()) {
            // TODO
        }
    }

}
