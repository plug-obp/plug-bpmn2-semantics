package plug.bpmn2.semantics2.domains.instances;

import org.eclipse.bpmn2.Activity;
import plug.bpmn2.semantics2.domains.values.LifecycleState;

import java.util.Collection;

public class ActivityInstance extends FlowElementInstance {
    Activity activity;
    LifecycleState lifecycleState;

    @Override
    public Collection<Object> getExecutableSteps(StructuralInstance configuration) {
        throw new RuntimeException("Activity instance steps are not yet supported");
    }
}
