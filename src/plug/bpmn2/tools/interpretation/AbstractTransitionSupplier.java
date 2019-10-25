package plug.bpmn2.tools.interpretation;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.transition.BPMNAbstractTransition;
import plug.bpmn2.interpretation.transition.action.ActionDefinition;
import plug.bpmn2.interpretation.transition.action.ActivityAction;
import plug.bpmn2.interpretation.transition.action.InstanceAction;
import plug.bpmn2.interpretation.transition.impl.ChangeStateImpl;
import plug.bpmn2.interpretation.transition.impl.CloseInstanceImpl;
import plug.bpmn2.interpretation.transition.impl.OpenInstanceImpl;
import plug.bpmn2.tools.BPMNToolKit;

import java.util.HashMap;
import java.util.Map;

public class AbstractTransitionSupplier {

    private final BPMNToolKit toolKit;
    private final Map<BaseElement, BPMNAbstractTransition> abstractTransitionMap;

    public AbstractTransitionSupplier(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
        abstractTransitionMap = new HashMap<>();
        DocumentRoot model = toolKit.getDocumentRoot();
        toolKit.log(this, "", "Building actions");
        new ActionSetBuilder().doSwitch(model);
    }

    public BPMNAbstractTransition getActionSet(BaseElement baseElement) {
        return abstractTransitionMap.computeIfAbsent(baseElement, BPMNAbstractTransition::new);
    }

    private class ActionSetBuilder extends Bpmn2Switch<Object> {

        private RootElement currentRootElement;
        private BPMNAbstractTransition currentAbstractTransition;

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
            currentAbstractTransition.getActionSet().add(action);
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
                currentAbstractTransition = getActionSet(rootElement);
                doSwitch(rootElement);
            }
            return 0;
        }

        @Override
        public Object caseFlowElementsContainer(FlowElementsContainer object) {
            toolKit.log(this, object, "Entering flow elements container");
            caseInstantiable(object);
            for (FlowElement flowElement : object.getFlowElements()) {
                doSwitch(flowElement);
            }
            return 0;
        }

        @Override
        public Object caseActivity(Activity object) {
            toolKit.log(this, object, "Entering activity");
            caseInstantiable(object);
            addAll(nominalActivityActions);
            return 0;
        }

        @Override
        public Object caseChoreography(Choreography object) {
            toolKit.log(this, object, "Entering choreography");
            for (FlowElement flowElement : object.getFlowElements()) {
                doSwitch(flowElement);
            }
            return 0;
        }
    }

}
