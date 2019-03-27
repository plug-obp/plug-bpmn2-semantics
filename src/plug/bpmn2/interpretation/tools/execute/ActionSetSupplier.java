package plug.bpmn2.interpretation.tools.execute;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.tools.BPMNRuntimeToolKit;
import plug.bpmn2.interpretation.transition.ActionSet;
import plug.bpmn2.interpretation.transition.action.ActionDefinition;
import plug.bpmn2.interpretation.transition.action.ActivityAction;
import plug.bpmn2.interpretation.transition.action.InstanceAction;
import plug.bpmn2.interpretation.transition.impl.ChangeStateImpl;
import plug.bpmn2.interpretation.transition.impl.CloseInstanceImpl;
import plug.bpmn2.interpretation.transition.impl.OpenInstanceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActionSetSupplier {

    private final BPMNRuntimeToolKit toolKit;
    private final Map<BaseElement, ActionSet> actionSetMap;

    public ActionSetSupplier(BPMNRuntimeToolKit toolKit) {
        this.toolKit = toolKit;
        actionSetMap = new HashMap<>();
        DocumentRoot model = toolKit.getDocumentRoot();
        toolKit.println(this, "", "Building actions");
        toolKit.increaseLogDepth();
        new ActionSetBuilder().doSwitch(model);
        toolKit.decreaseLogDepth();
    }

    public ActionSet getActionSet(BaseElement baseElement) {
        return actionSetMap.computeIfAbsent(baseElement, ActionSet::new);
    }

    private class ActionSetBuilder extends Bpmn2Switch<Object> {

        private RootElement currentRootElement;
        private ActionSet currentActionSet;

        private final ActivityAction[] nominalActivityActions;
        private final InstanceAction.Close closeAction;

        public ActionSetBuilder() {
            nominalActivityActions = new ActivityAction[3];
            nominalActivityActions[0] = new ChangeStateImpl(ActivityState.ACTIVE);
            nominalActivityActions[1] = new ChangeStateImpl(ActivityState.COMPLETING);
            nominalActivityActions[2] = new ChangeStateImpl(ActivityState.COMPLETED);
            closeAction = new CloseInstanceImpl();
        }

        private void add(ActionDefinition action) {
            currentActionSet.getActionSet().add(action);
        }

        private void addAll(ActionDefinition[] actionDefinitions) {
            for (ActionDefinition action : actionDefinitions) {
                add(action);
            }
        }

        private void caseInstantiable(BaseElement baseElement) {
            add(new OpenInstanceImpl(baseElement));
            add(closeAction);
        }

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            for (RootElement rootElement : object.getDefinitions().getRootElements()) {
                currentRootElement = rootElement;
                currentActionSet = getActionSet(rootElement);
                doSwitch(rootElement);
            }
            return 0;
        }

        @Override
        public Object caseFlowElementsContainer(FlowElementsContainer object) {
            toolKit.println(this, object, "Entering flow elements container");
            toolKit.increaseLogDepth();
            caseInstantiable(object);
            for (FlowElement flowElement : object.getFlowElements()) {
                doSwitch(flowElement);
            }
            toolKit.decreaseLogDepth();
            return 0;
        }

        @Override
        public Object caseActivity(Activity object) {
            toolKit.println(this, object, "Entering activity");
            toolKit.increaseLogDepth();
            caseInstantiable(object);
            addAll(nominalActivityActions);
            toolKit.decreaseLogDepth();
            return 0;
        }

        @Override
        public Object caseChoreography(Choreography object) {
            toolKit.println(this, object, "Entering choreography");
            toolKit.increaseLogDepth();
            for (FlowElement flowElement : object.getFlowElements()) {
                doSwitch(flowElement);
            }
            toolKit.decreaseLogDepth();
            return 0;
        }
    }

}
