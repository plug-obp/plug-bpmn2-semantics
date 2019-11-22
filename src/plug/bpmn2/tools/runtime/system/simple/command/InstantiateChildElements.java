package plug.bpmn2.tools.runtime.system.simple.command;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.runtime.system.simple.common.Elements;
import plug.bpmn2.tools.runtime.system.simple.common.NonNull;

import java.util.List;
import java.util.Set;

public class InstantiateChildElements extends AbstractCommand {

    private final InternalSwitch internalSwitch = new InternalSwitch();

    public InstantiateChildElements(BPMNModelHandler model) {
        super(model);
    }

    @Override
    public void execute() {
        BPMNRuntimeInstance scope = getScope();
        Set<EObject> childElements = getModel().ownership.getTargetSet(scope.getBaseElement());
        for (EObject childElement : childElements) {
            internalSwitch.doSwitch(childElement);
        }
    }

    private class InternalSwitch extends Bpmn2Switch<Object> {

        @Override
        public Object caseProcess(Process process) {
            Elements.instantiateProcess(getModel(), getState(), getScope(), process);
            return NonNull.OBJECT;
        }

        @Override
        public Object caseTask(Task object) {
            return NonNull.OBJECT;
        }

        @Override
        public Object defaultCase(EObject object) {
            raiseWarning("Unexpected Child Element", object);
            return NonNull.OBJECT;
        }

    }

}
