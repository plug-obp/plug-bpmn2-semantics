package plug.bpmn2.interpretation.model.instance.data;

import org.eclipse.bpmn2.MessageFlow;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

public class MessageFlowData extends FlowDataBase {

    private final MessageFlow messageFlow;
    private final boolean isPresent;

    public MessageFlowData(
            MessageFlow messageFlow,
            BPMNRuntimeInstance sourceParent,
            BPMNRuntimeInstance targetParent,
            boolean isPresent
    ) {
        super(sourceParent, targetParent);
        this.messageFlow = messageFlow;
        this.isPresent = isPresent;
    }

    @Override
    public MessageFlow getBaseElement() {
        return messageFlow;
    }

    @Override
    public Boolean getData() {
        return isPresent;
    }

}
