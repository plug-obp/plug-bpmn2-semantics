package obp2.bpmn2.model.action;

import java.util.Collection;

public class FlowActionPool {

    private final BPMN2FlowAction[] flowActionArray;

    public FlowActionPool(Collection<BPMN2FlowAction> flowActionCollection) {
        flowActionArray = new BPMN2FlowAction[flowActionCollection.size()];
        int id = 0;
        for (BPMN2FlowAction flowAction : flowActionCollection) {
            flowAction.setId(id);
            flowActionArray[id] = flowAction;
            id++;
        }
    }

    public BPMN2FlowAction[] getFlowActionArray() {
        return flowActionArray;
    }

    public BPMN2FlowAction getFlowAction(int id) {
        return flowActionArray[id];
    }

}
