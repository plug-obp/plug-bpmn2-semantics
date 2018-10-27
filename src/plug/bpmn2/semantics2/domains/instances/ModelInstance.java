package plug.bpmn2.semantics2.domains.instances;

import org.eclipse.bpmn2.DataStore;
import org.eclipse.bpmn2.RootElement;
import plug.bpmn2.semantics2.domains.values.DataStoreValue;

import java.util.*;

public class ModelInstance extends StructuralInstance {
    Map<DataStore, DataStoreValue> dataStoreValueMap = new IdentityHashMap<>();
    Map<RootElement, RootElementInstance> rootElementValueMap = new IdentityHashMap<>();

    public void addDataStoreValue(DataStore dataStore, DataStoreValue value) {
        dataStoreValueMap.put(dataStore, value);
    }

    public void addRootElementValue(RootElement rootElement, RootElementInstance instance) {
        rootElementValueMap.put(rootElement, instance);
    }

    public Map<RootElement, RootElementInstance> getRootElementValueMap() {
        return rootElementValueMap;
    }

    @Override
    public Collection<Object> getExecutableSteps(StructuralInstance configuration) {
        ModelInstance conf = (ModelInstance) configuration;
        List<Object> steps = new ArrayList<>();
        for (RootElementInstance instance : conf.getRootElementValueMap().values()) {
            steps.addAll(instance.getExecutableSteps(configuration));
        }
        return steps;
    }
}
