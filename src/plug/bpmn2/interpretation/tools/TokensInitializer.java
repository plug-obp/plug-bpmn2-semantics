package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;


public class TokensInitializer {

    private final BPMNRuntimeToolKit toolKit;
    private final TokenPool tokenPool;
    private final InternalSwitch internalSwitch;

    private FlowElementsContainerInstance instance;

    public TokensInitializer(BPMNRuntimeToolKit toolKit) {
        this.toolKit = toolKit;
        tokenPool = toolKit.getTokenPool();
        internalSwitch = new InternalSwitch();
    }

    public void initialize(FlowElementsContainerInstance instance) {
        toolKit.println(this.getClass().toString(), instance.toString(), "Starting");
        this.instance = instance;
        for (FlowElement flowElement : instance.getBaseElement().getFlowElements()) {
            internalSwitch.doSwitch(flowElement);
        }
    }

    private class InternalSwitch extends Bpmn2Switch<Object> {

        private final Object NON_NULL = 0;

        @Override
        public Object caseStartEvent(StartEvent startEvent) {
            if (!startEvent.getIncoming().isEmpty()) {
                toolKit.println(this.getClass().toString(), startEvent.toString(), "Has Incoming Sequence Flow");
            } else if (!startEvent.getIncomingConversationLinks().isEmpty()) {
                toolKit.println(this.getClass().toString(), startEvent.toString(), "Has Incoming Conversation Link");
            } else {
                toolKit.println(this.getClass().toString(), startEvent.toString(), "Is a valid initial start event");
                for (SequenceFlow initialSequenceFlow : startEvent.getOutgoing()) {
                    toolKit.println(this.getClass().toString(), initialSequenceFlow.toString(), "Adding Token");
                    instance.getTokenSet().add(tokenPool.getToken(initialSequenceFlow));
                }
            }
            return NON_NULL;
        }

    }

}
