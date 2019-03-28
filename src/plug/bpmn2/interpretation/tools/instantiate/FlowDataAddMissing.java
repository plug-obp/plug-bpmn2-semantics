package plug.bpmn2.interpretation.tools.instantiate;

import org.eclipse.bpmn2.MessageFlow;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.CollaborationInstance;
import plug.bpmn2.interpretation.model.instance.data.MessageFlowData;
import plug.bpmn2.interpretation.tools.BPMNToolKit;
import plug.bpmn2.interpretation.tools.walker.BPMNInstanceAspectHandler;
import plug.bpmn2.interpretation.tools.walker.BPMNRuntimeInstanceWalker;

import java.util.Set;

public class FlowDataAddMissing {

    private final BPMNToolKit toolKit;

    private final BPMNRuntimeInstanceWalker walker;

    private BPMNModelRuntimeState runtimeState;

    public FlowDataAddMissing(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
        walker = new BPMNRuntimeInstanceWalker(new InternalHandler(), new InternalVisitor());
    }

    public void initializeFlowData(BPMNModelRuntimeState runtimeState) {
        toolKit.println(this, "", "Starting");
        toolKit.increaseLogDepth();

        this.runtimeState = runtimeState;
        for (BPMNRuntimeInstance rootInstance : runtimeState.getRootInstances()) {
            walker.walkInstanceTree(rootInstance);
        }

        toolKit.decreaseLogDepth();
    }

    private class InternalHandler implements BPMNInstanceAspectHandler {

        @Override
        public void handleCollaborationAspect(CollaborationInstance instance) {
            toolKit.println(this, instance.getBaseElement(), "Entering Collaboration");
            for (MessageFlow messageFlow : instance.getBaseElement().getMessageFlows()) {
                toolKit.println(this, messageFlow, "Adding MessageFlow");
                Set<BPMNRuntimeInstance> sourceSet = toolKit
                        .getInstanceMap()
                        .getEnclosingInstances(messageFlow.getSourceRef());
                Set<BPMNRuntimeInstance> targetSet = toolKit
                        .getInstanceMap()
                        .getEnclosingInstances(messageFlow.getTargetRef());
                // TODO Check which are actually missing and needed
                for (BPMNRuntimeInstance sourceRef : sourceSet) {
                    for (BPMNRuntimeInstance targetRef : targetSet) {
                        MessageFlowData messageFlowData = new MessageFlowData(
                                messageFlow,
                                sourceRef,
                                targetRef,
                                false
                        );
                        runtimeState.getMessageFlowDataSet().add(messageFlowData);
                    }
                }
            }
        }
    }

    private class InternalVisitor implements BPMNInstanceVisitor {

    }

}
