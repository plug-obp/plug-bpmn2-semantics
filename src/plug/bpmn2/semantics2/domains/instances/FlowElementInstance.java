package plug.bpmn2.semantics2.domains.instances;

import org.eclipse.bpmn2.Property;
import plug.bpmn2.semantics2.domains.values.Value;

import java.util.IdentityHashMap;
import java.util.Map;

public abstract class FlowElementInstance extends StructuralInstance {
    Map<Property, Value> property2value = new IdentityHashMap<>();
}
