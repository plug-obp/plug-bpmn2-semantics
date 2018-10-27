package plug.bpmn2.semantics2.domains.instances;

import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Process;

import java.util.*;

public class CollaborationInstance extends RootElementInstance {
    Collaboration collaboration;
    Map<Process, ProcessInstance> processes = new IdentityHashMap<>();

    public CollaborationInstance(Collaboration collaboration) {
        this.collaboration = collaboration;
    }

    public void putProcessInstance(Process process, ProcessInstance instance) {
        processes.put(process, instance);
    }

    public Map<Process, ProcessInstance> getProcesses() {
        return processes;
    }

    @Override
    public Collection<Object> getExecutableSteps(StructuralInstance configuration) {
        CollaborationInstance conf = (CollaborationInstance) configuration;
        List<Object> steps = new ArrayList<>();
        for (RootElementInstance instance : conf.getProcesses().values()) {
            steps.addAll(instance.getExecutableSteps(configuration));
        }
        return steps;
    }
}
