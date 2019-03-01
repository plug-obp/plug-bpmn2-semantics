package plug.bpmn2.interpretation.model;

import org.eclipse.bpmn2.SequenceFlow;

public class BPMNRuntimeToken implements BPMNRuntimeBaseElement<SequenceFlow> {

    private final SequenceFlow baseElement;

    public BPMNRuntimeToken(SequenceFlow baseElement) {
        this.baseElement = baseElement;
    }

    @Override
    public SequenceFlow baseElement() {
        return baseElement;
    }


}
