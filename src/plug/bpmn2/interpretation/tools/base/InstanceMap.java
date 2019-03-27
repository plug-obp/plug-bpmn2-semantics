package plug.bpmn2.interpretation.tools.base;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.tools.BPMNRuntimeToolKit;
import plug.bpmn2.interpretation.tools.walker.BPMNInstanceAspectHandler;
import plug.bpmn2.interpretation.tools.walker.BPMNRuntimeInstanceWalker;

import java.util.*;

public class InstanceMap {

    private final BPMNRuntimeToolKit toolKit;

    private final BPMNRuntimeInstanceWalker instanceFetcher;
    private final Map<BaseElement, Set<BPMNRuntimeInstance>> instanceMap;

    public InstanceMap(BPMNRuntimeToolKit toolKit) {
        this.toolKit = toolKit;
        instanceFetcher = new BPMNRuntimeInstanceWalker(new InstanceFetcher());
        instanceMap = new HashMap<>();
    }

    public void load(BPMNModelRuntimeState modelRuntimeState) {
        instanceMap.clear();
        toolKit.println(this, "", "Computing instance map");
        for (BPMNRuntimeInstance rootInstance : modelRuntimeState.getRootInstances()) {
            instanceFetcher.walkInstanceTree(rootInstance);
        }
    }

    public Set<BPMNRuntimeInstance> getInstances(BaseElement baseElement) {
        Set<BPMNRuntimeInstance> instanceSet = instanceMap.computeIfAbsent(baseElement, k -> new HashSet<>());
        return instanceSet;
    }

    public Set<BPMNRuntimeInstance> getEnclosingInstances(InteractionNode interactionNode) {
        if (!(interactionNode instanceof BaseElement)) {
            toolKit.println(this, interactionNode, "Unexpected instance of interactionNode, not a BaseElement");
            return Collections.emptySet();
        }
        BaseElement baseElement = (BaseElement) interactionNode;
        Set<BPMNRuntimeInstance> instanceSet = instanceMap.get(baseElement);
        if (instanceSet == null) {
            instanceSet = new HashSet<>();
        } else if (!instanceSet.isEmpty()) {
            return instanceSet;
        }

        Set<BaseElement> parentSet = toolKit.getParentMap().getParents(baseElement);
        if (parentSet.isEmpty()) {
            toolKit.println(this, interactionNode, "No enclosing instance found");
            return Collections.emptySet();
        }
        for (BaseElement parent : parentSet) {
            Set<BPMNRuntimeInstance> parentInstanceSet = instanceMap.get(parent);
            if (parentInstanceSet != null) {
                    instanceSet.addAll(parentInstanceSet);
            }
        }
        return instanceSet;
    }

    private class InstanceFetcher implements BPMNInstanceAspectHandler {

        @Override
        public void handleAllInstances(BPMNRuntimeInstance instance) {
            BaseElement baseElement = instance.getBaseElement();
            getInstances(baseElement).add(instance);
        }

    }

}
