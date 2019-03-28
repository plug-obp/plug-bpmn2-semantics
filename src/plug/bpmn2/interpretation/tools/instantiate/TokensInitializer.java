package plug.bpmn2.interpretation.tools.instantiate;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;
import plug.bpmn2.interpretation.tools.BPMNToolKit;


public class TokensInitializer {

    private final BPMNToolKit toolKit;
    private final TokenPool tokenPool;
    private final InternalSwitch internalSwitch;

    private FlowElementsContainerInstance instance;

    public TokensInitializer(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
        tokenPool = toolKit.getTokenPool();
        internalSwitch = new InternalSwitch();
    }

    public void initialize(FlowElementsContainerInstance instance) {
        toolKit.println(this, instance.getBaseElement(), "Entering");
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
                toolKit.println(this, startEvent, "Has Incoming Sequence Flow");
                return NON_NULL;
            } else if (!startEvent.getIncomingConversationLinks().isEmpty()) {
                toolKit.println(this, startEvent, "Has Incoming Conversation Link");
                return NON_NULL;
            } else if (!startEvent.getEventDefinitions().isEmpty()) {
                toolKit.println(this, startEvent, "Is awaiting event(s)");
                return NON_NULL;
            } else if (!startEvent.getEventDefinitionRefs().isEmpty()) {
                toolKit.println(this, startEvent, "Is awaiting event(s) ref(s)");
                return NON_NULL;
            }
            toolKit.println(this, startEvent, "Is a valid initial start event");
            for (SequenceFlow initialSequenceFlow : startEvent.getOutgoing()) {
                toolKit.println(this, initialSequenceFlow, "Adding Token");
                instance.getTokenSet().add(tokenPool.getToken(initialSequenceFlow));
            }
            return NON_NULL;
        }

    }

}
