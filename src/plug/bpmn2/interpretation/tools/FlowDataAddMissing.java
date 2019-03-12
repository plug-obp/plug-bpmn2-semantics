package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.MessageFlow;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
import plug.bpmn2.interpretation.tools.walker.BPMNInstanceAspectHandler;
import plug.bpmn2.interpretation.tools.walker.BPMNRuntimeInstanceWalker;

public class FlowDataAddMissing {

    private final BPMNRuntimeToolKit toolKit;

    private final BPMNRuntimeInstanceWalker walker;

    private BPMNModelRuntimeState runtimeState;

    public FlowDataAddMissing(BPMNRuntimeToolKit toolKit) {
        this.toolKit = toolKit;
        walker = new BPMNRuntimeInstanceWalker(new InternalHandler(), new InternalVisitor());
    }

    public void initializeFlowData(BPMNModelRuntimeState runtimeState) {
        toolKit.println(this.getClass().toString(), runtimeState.toString(), "Starting");
        this.runtimeState = runtimeState;
        walker.walkInstanceTree(runtimeState.getRoot());
    }

    private class InternalHandler implements BPMNInstanceAspectHandler {

        @Override
        public void handleCollaborationAspect(CollaborationInstance instance) {
            toolKit.println(this.getClass().toString(), instance.toString(), "Entering Collaboration");
            for (MessageFlow messageFlow : instance.getBaseElement().getMessageFlows()) {
                toolKit.println(this.getClass().toString(), messageFlow.toString(), "Adding MessageFlow");
                MessageFlowData messageFlowData = new MessageFlowData(
                        messageFlow,
                        null,
                        null,
                        false
                );
                runtimeState.getMessageFlowDataSet().add(messageFlowData);
            }
        }
    }

    private class InternalVisitor implements BPMNInstanceVisitor {

    }

}
