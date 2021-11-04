package plug.bpmn2.semantics.state.instance.data;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.semantics.state.BPMNRuntimeBaseElement;

public class Token implements BPMNRuntimeBaseElement {

    private final SequenceFlow baseElement;

    public Token(SequenceFlow baseElement) {
        this.baseElement = baseElement;
    }

    @Override
    public SequenceFlow getBaseElement() {
        return baseElement;
    }

}
