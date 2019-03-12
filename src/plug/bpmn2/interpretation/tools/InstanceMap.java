package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.tools.walker.BPMNInstanceAspectHandler;
import plug.bpmn2.interpretation.tools.walker.BPMNRuntimeInstanceWalker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InstanceMap {

    private final BPMNRuntimeToolKit toolKit;
    private final BPMNRuntimeInstanceWalker walker;
    private final Map<BaseElement, Set<BPMNRuntimeInstance>> instanceMap;

    public InstanceMap(BPMNRuntimeToolKit toolKit) {
        this.toolKit = toolKit;
        walker = new BPMNRuntimeInstanceWalker(
                new InternalHandler(),
                new InternalVisitor()
        );
        instanceMap = new HashMap<>();
    }

    public void load(BPMNModelRuntimeState modelRuntimeState) {
        instanceMap.clear();
        toolKit.println(this.getClass().toString(), modelRuntimeState.toString(), "Computing instance map");
        walker.walkInstanceTree(modelRuntimeState.getRoot());
    }

    public Set<BPMNRuntimeInstance> getInstances(BaseElement baseElement) {
        Set<BPMNRuntimeInstance> instanceSet = instanceMap.computeIfAbsent(baseElement, k -> new HashSet<>());
        return instanceSet;
    }

    private class InternalHandler implements BPMNInstanceAspectHandler {

        @Override
        public void handleAllInstances(BPMNRuntimeInstance instance) {
            BaseElement baseElement = instance.getBaseElement();
            getInstances(baseElement).add(instance);
        }

    }

    private class InternalVisitor implements BPMNInstanceVisitor {

    }


}
