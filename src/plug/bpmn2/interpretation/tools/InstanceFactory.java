package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.instance.data.ActivityState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.*;
import plug.bpmn2.interpretation.model.instance.impl.*;

import java.util.LinkedList;

public class InstanceFactory {

    private final BPMNRuntimeToolKit toolKit;
    private final InternalSwitch internalSwitch;
    private final TokensInitializer tokensInitializer;

    public InstanceFactory(BPMNRuntimeToolKit toolKit) {
        this.toolKit = toolKit;
        this.tokensInitializer = toolKit.getTokensInitializer();
        internalSwitch = new InternalSwitch();
    }

    public BPMNRuntimeInstance instantiate(DocumentRoot model) {
        return instantiate(null, model.getRootElement());
    }

    public BPMNRuntimeInstance instantiate(BPMNRuntimeInstance parent, BaseElement baseElement) {
        toolKit.println(this.getClass().toString(), baseElement.toString(), "Recursive instantiations");
        internalSwitch.getParentStack().clear();
        internalSwitch.getParentStack().push(parent);
        return internalSwitch.doSwitch(baseElement);
    }

    private class InternalSwitch extends Bpmn2Switch<BPMNRuntimeInstance> {

        private final LinkedList<BPMNRuntimeInstance> parentStack;

        public InternalSwitch() {
            parentStack = new LinkedList<>();
        }

        public LinkedList<BPMNRuntimeInstance> getParentStack() {
            return parentStack;
        }

        private BPMNRuntimeInstance getParent() {
            if (parentStack.isEmpty()) {
                throw new IllegalStateException(
                        "Empty getParent stack"
                );
            }
            return parentStack.getLast();
        }

        private BPMNRuntimeInstance getNonNullParent() {
            BPMNRuntimeInstance parent = getParent();
            if (parent == null) {
                throw new IllegalArgumentException(
                        "Expected a non null getParent (not a legal model root element)"
                );
            }
            return parent;
        }

        private FlowElementsContainerInstance getActivityParent() {
            BPMNRuntimeInstance parent = getNonNullParent();
            if (!(parent instanceof FlowElementsContainerInstance)) {
                throw new IllegalArgumentException(
                        "The getParent of an ActivityInstance has to be a FlowElementsContainerInstance"
                );
            }
            return (FlowElementsContainerInstance) parent;
        }

        @Override
        public CollaborationInstance caseCollaboration(Collaboration object) {
            toolKit.println(this.getClass().toString(), object.toString(), "Instantiating collaboration");
            CollaborationInstance result = new CollaborationInstanceImpl(getParent(), object);
            parentStack.push(result);
            for (Participant participant : object.getParticipants()) {
                Process process = participant.getProcessRef();
                for (int i = 0; i < participant.getParticipantMultiplicity().getMinimum(); i++) {
                    doSwitch(process);
                }
            }
            parentStack.removeLast();
            return result;
        }

        @Override
        public ChoreographyInstance caseChoreography(Choreography object) {
            toolKit.println(this.getClass().toString(), object.toString(), "Instantiating choreography");
            ChoreographyInstance result = new ChoreographyInstanceImpl(getParent(), object);
            tokensInitializer.initialize(result);
            return result;
        }

        @Override
        public ProcessInstance caseProcess(Process object) {
            toolKit.println(this.getClass().toString(), object.toString(), "Instantiating process");
            ProcessInstance result = new ProcessInstanceImpl(getParent(), object);
            tokensInitializer.initialize(result);
            return result;
        }

        @Override
        public SubProcessInstance caseSubProcess(SubProcess object) {
            toolKit.println(this.getClass().toString(), object.toString(), "Instantiating subProcess");
            SubProcessInstance result = new SubProcessInstanceImpl(getActivityParent(), object, ActivityState.ACTIVE);
            tokensInitializer.initialize(result);
            return result;
        }

        @Override
        public TaskInstance caseTask(Task object) {
            toolKit.println(this.getClass().toString(), object.toString(), "Instantiating task");
            return new TaskInstanceImpl(getActivityParent(), object, ActivityState.ACTIVE);
        }

    }

}
