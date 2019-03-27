package plug.bpmn2.interpretation.transition.impl;

import plug.bpmn2.interpretation.transition.action.ActionDefinition;
import plug.bpmn2.interpretation.transition.action.ActionSequence;

import java.util.LinkedList;
import java.util.List;

public class ActionSequenceImpl implements ActionSequence {

    private final List<ActionDefinition> actionList;

    public ActionSequenceImpl() {
        actionList = new LinkedList<>();
    }

    @Override
    public List<ActionDefinition> getActionList() {
        return actionList;
    }

}
