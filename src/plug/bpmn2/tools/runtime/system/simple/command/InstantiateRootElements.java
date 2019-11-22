package plug.bpmn2.tools.runtime.system.simple.command;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.CollaborationInstance;
import plug.bpmn2.interpretation.model.instance.impl.CollaborationInstanceImpl;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.runtime.system.simple.common.Elements;
import plug.bpmn2.tools.runtime.system.simple.common.NonNull;

import java.util.List;

public class InstantiateRootElements extends AbstractCommand {

    private final InternalSwitch internalSwitch = new InternalSwitch();
    private final InstantiateChildElements instantiateChildElements;

    public InstantiateRootElements(BPMNModelHandler model, InstantiateChildElements instantiateChildElements) {
        super(model);
        this.instantiateChildElements = instantiateChildElements;
    }

    public InstantiateRootElements(BPMNModelHandler model) {
        this(model, new InstantiateChildElements(model));
    }

    @Override
    public void execute() {
        for (EObject rootElement : getModel().ownership.getFloorSet(0)) {
            internalSwitch.doSwitch(rootElement);
        }
    }

    private class InternalSwitch extends Bpmn2Switch<Object> {

        @Override
        public Object caseCollaboration(Collaboration object) {
            CollaborationInstance instance = new CollaborationInstanceImpl(null, object);
            BPMNRuntimeState state = getState();
            state.getRootInstances().add(instance);
            instantiateChildElements.execute(state, instance);
            return NonNull.OBJECT;
        }

        @Override
        public Object caseProcess(Process process) {
            Elements.instantiateProcess(getModel(), getState(), getScope(), process);
            return NonNull.OBJECT;
        }

        @Override
        public Object defaultCase(EObject object) {
            raiseWarning("Unexpected Root Element", object);
            return NonNull.OBJECT;
        }

    }
}
