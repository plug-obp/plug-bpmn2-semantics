package obp2.bpmn2.model.propositions;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.FlowNode;

public class ActionProps {

    private final BPMN2ProcessedModel model;
    private final BPMN2FlowAction flowAction;

    public ActionProps(BPMN2ProcessedModel model, BPMN2FlowAction flowAction) {
        this.model = model;
        this.flowAction = flowAction;
    }

    public boolean sendsSignal(String signalName) {
        if (flowAction == null || flowAction.getType() != BPMN2FlowAction.Type.THROW_SIGNAL) return false;
        Event event = (Event) flowAction.getFlowNode();
        Object eventId = model.getSignalData().getSignal(event);
        return signalName.equals(eventId.toString());
    }

    public boolean endsTask(String taskName) {
        if (flowAction == null || flowAction.getType() != BPMN2FlowAction.Type.END_TASK) return false;
        FlowNode task = flowAction.getFlowNode();
        return task.getName().equals(taskName);
    }

}
