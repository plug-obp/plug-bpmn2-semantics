package plug.bpmn2.tools.walker;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.ActivityInstance;
import plug.bpmn2.interpretation.model.instance.CollaborationInstance;
import plug.bpmn2.interpretation.model.instance.FlowElementsContainerInstance;

public interface BPMNInstanceAspectHandler {

    default void handleAllInstances(BPMNRuntimeInstance instance) {}

    default void handleFlowElementsContainerAspect(FlowElementsContainerInstance instance) {}
    default void handleActivityAspect(ActivityInstance instance) {}
    default void handleCollaborationAspect(CollaborationInstance instance) {}

    static void handle(BPMNInstanceAspectHandler handler, BPMNRuntimeInstance instance) {
        handler.handleAllInstances(instance);
        if (instance instanceof FlowElementsContainerInstance) {
            handler.handleFlowElementsContainerAspect((FlowElementsContainerInstance) instance);
        }
        if (instance instanceof CollaborationInstance) {
            handler.handleCollaborationAspect((CollaborationInstance) instance);
        }
        if (instance instanceof ActivityInstance) {
            handler.handleActivityAspect((ActivityInstance) instance);
        }
    }

}
