package obp2.bpmn2.plugin;

import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.semantics.runtime.BPMN2RuntimeConfiguration;
import obp2.core.defaults.FiredTransition;

import java.util.Set;

public class BPMN2FiredTransition extends FiredTransition<BPMN2RuntimeConfiguration, BPMN2FlowAction, Void>{

    public BPMN2FiredTransition(BPMN2RuntimeConfiguration source, BPMN2RuntimeConfiguration target, BPMN2FlowAction fired) {
        super(source, target, fired);
    }

    public BPMN2FiredTransition(BPMN2RuntimeConfiguration source, Set<BPMN2RuntimeConfiguration> targets, BPMN2FlowAction fired) {
        super(source, targets, fired);
    }

}
