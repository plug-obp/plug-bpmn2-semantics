package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
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

    public BPMNModelRuntimeState instantiate(DocumentRoot model) {
        BPMNModelRuntimeState initialState = new BPMNModelRuntimeState();
        Definitions definitions = model.getDefinitions();
        for (RootElement rootElement : definitions.getRootElements()) {
            if (toolKit.getParentMap().isRoot(rootElement)) {
                BPMNRuntimeInstance instance = instantiate(null, rootElement);
                if (instance != null) {
                    initialState.getRootInstances().add(instance);
                }
            } else {
                toolKit.println(this, rootElement, "Referenced in another root element");
            }
        }
        return initialState;
    }

    public BPMNRuntimeInstance instantiate(BPMNRuntimeInstance parent, BaseElement baseElement) {
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
            toolKit.println(this, object, "Instantiating collaboration");
            toolKit.increaseLogDepth();

            CollaborationInstance result = new CollaborationInstanceImpl(getParent(), object);
            parentStack.push(result);
            for (Participant participant : object.getParticipants()) {
                Process process = participant.getProcessRef();
                int min;
                if (participant.getParticipantMultiplicity() == null) {
                    min = 1;
                } else {
                    min = participant.getParticipantMultiplicity().getMinimum();
                }
                for (int i = 0; i < min; i++) {
                    BPMNRuntimeInstance instance = doSwitch(process);
                    result.getChildInstanceSet().add(instance);
                }
            }
            parentStack.removeLast();
            toolKit.decreaseLogDepth();
            return result;
        }

        @Override
        public ChoreographyInstance caseChoreography(Choreography object) {
            toolKit.println(this, object, "Instantiating choreography");
            toolKit.increaseLogDepth();

            ChoreographyInstance result = new ChoreographyInstanceImpl(getParent(), object);
            tokensInitializer.initialize(result);

            toolKit.decreaseLogDepth();
            return result;
        }

        @Override
        public ProcessInstance caseProcess(Process object) {
            toolKit.println(this, object, "Instantiating process");
            toolKit.increaseLogDepth();

            ProcessInstance result = new ProcessInstanceImpl(getParent(), object);
            tokensInitializer.initialize(result);

            toolKit.decreaseLogDepth();
            return result;
        }

        @Override
        public SubProcessInstance caseSubProcess(SubProcess object) {
            toolKit.println(this, object, "Instantiating subProcess");
            toolKit.increaseLogDepth();

            SubProcessInstance result = new SubProcessInstanceImpl(getActivityParent(), object, ActivityState.ACTIVE);
            tokensInitializer.initialize(result);

            toolKit.decreaseLogDepth();
            return result;
        }

        @Override
        public TaskInstance caseTask(Task object) {
            toolKit.println(this, object, "Instantiating task");
            toolKit.increaseLogDepth();

            TaskInstance result = new TaskInstanceImpl(getActivityParent(), object, ActivityState.ACTIVE);

            toolKit.decreaseLogDepth();
            return result;
        }

    }

}
