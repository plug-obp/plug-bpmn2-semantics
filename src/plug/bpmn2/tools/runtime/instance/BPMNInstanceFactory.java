package plug.bpmn2.tools.runtime.instance;

import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.*;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.model.instance.impl.*;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.LinkedList;
import java.util.List;

public class BPMNInstanceFactory {

    private BPMNModelHandler modelHandler;
    private BPMNRuntimeState state;
    private BPMNRuntimeInstance owner;
    private BaseElement parent;

    private InternalSwitch internalSwitch = new InternalSwitch();

    private void setArguments(BPMNModelHandler modelHandler,
                              BPMNRuntimeState state,
                              BPMNRuntimeInstance owner,
                              BaseElement parent) {
        this.modelHandler = modelHandler;
        this.state = state;
        this.owner = owner;
        this.parent = parent;
    }

    public boolean check(BPMNModelHandler modelHandler,
                         BPMNRuntimeState state,
                         BPMNRuntimeInstance owner,
                         BaseElement parent) {
        return true;
    }

    public void create(BPMNModelHandler modelHandler,
                       BPMNRuntimeState state,
                       BPMNRuntimeInstance owner,
                       BaseElement parent) {
        setArguments(modelHandler, state, owner, parent);
        List<BPMNRuntimeInstance> targetList = owner == null ?
                state.getRootInstanceList() :
                owner.getChildInstanceList();
        targetList.add(internalSwitch.doSwitch(parent));
    }

    class InternalSwitch extends Bpmn2Switch<BPMNRuntimeInstance> {

        private LinkedList<BPMNRuntimeInstance> ownerStack = new LinkedList<>();

        private void pushOwner(BPMNRuntimeInstance owner) {
            ownerStack.addLast(owner);
        }

        private void popOwner() {
            ownerStack.removeLast();
        }

        private BPMNRuntimeInstance getOwner() {
            return ownerStack.isEmpty() ? owner : ownerStack.getLast();
        }

        private FlowElementsContainerInstance getFECOwner() {
            BPMNRuntimeInstance owner = getOwner();
            if (owner == null) return null;
            if (!(owner instanceof FlowElementsContainerInstance)) {
                throw new IllegalStateException("Expected a FlowElementsContainer as instance owner");
            }
            return (FlowElementsContainerInstance) owner;
        }

        private void switchOwned(BPMNRuntimeInstance owner) {
            pushOwner(owner);
            for (EObject owned : modelHandler.ownership.getTargetSet(owner.getBaseElement())) {
                BPMNRuntimeInstance ownedInstance = doSwitch(owned);
                if (ownedInstance != null) {
                    owner.getChildInstanceList().add(ownedInstance);
                }
            }
            popOwner();
        }

        @Override
        public BPMNRuntimeInstance caseChoreography(Choreography object) {
            ChoreographyInstance result = new ChoreographyInstanceImpl(getOwner(), object);
            switchOwned(result);
            return result;
        }

        @Override
        public BPMNRuntimeInstance caseCollaboration(Collaboration object) {
            CollaborationInstance result = new CollaborationInstanceImpl(getOwner(), object);
            switchOwned(result);
            return result;
        }

        @Override
        public BPMNRuntimeInstance caseProcess(Process object) {
            ProcessInstance result = new ProcessInstanceImpl(getOwner(), object);
            switchOwned(result);
            return result;
        }

        @Override
        public BPMNRuntimeInstance caseSubProcess(SubProcess object) {
            SubProcessInstance result = new SubProcessInstanceImpl(getFECOwner(), object, ActivityState.READY);
            switchOwned(result);
            return result;
        }

        @Override
        public BPMNRuntimeInstance caseTask(Task object) {
            TaskInstance result = new TaskInstanceImpl(getFECOwner(), object, ActivityState.READY);
            switchOwned(result);
            return result;
        }
    }

}
