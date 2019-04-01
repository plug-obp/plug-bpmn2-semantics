package plug.bpmn2.tools.instance;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.tools.BPMNToolKit;
import plug.bpmn2.tools.walker.BPMNInstanceAspectHandler;
import plug.bpmn2.tools.walker.BPMNRuntimeInstanceWalker;

import java.util.*;

public class TokenPool {

    private final Map<SequenceFlow, Token> sequenceFlowTokenMap;

    public TokenPool() {
        sequenceFlowTokenMap = new HashMap<>();
    }

    public Token getToken(SequenceFlow sequenceFlow) {
        Token result = sequenceFlowTokenMap.get(sequenceFlow);
        if (result == null) {
            result = new Token(sequenceFlow);
            sequenceFlowTokenMap.put(sequenceFlow, result);
        }
        return result;
    }

    public static class InstanceMap {

        private final BPMNToolKit toolKit;

        private final BPMNRuntimeInstanceWalker instanceFetcher;
        private final Map<BaseElement, Set<BPMNRuntimeInstance>> instanceMap;
        private BPMNModelRuntimeState lastState;

        public InstanceMap(BPMNToolKit toolKit) {
            this.toolKit = toolKit;
            instanceFetcher = new BPMNRuntimeInstanceWalker(new InstanceFetcher());
            instanceMap = new HashMap<>();
        }

        public void load(BPMNModelRuntimeState modelRuntimeState) {
            if (!Objects.equals(modelRuntimeState, lastState)) {
                instanceMap.clear();
                toolKit.log(this, "", "Computing instance map");
                for (BPMNRuntimeInstance rootInstance : modelRuntimeState.getRootInstances()) {
                    instanceFetcher.walkInstanceTree(rootInstance);
                }
            }
        }

        public Set<BPMNRuntimeInstance> getInstances(BaseElement baseElement) {
            Set<BPMNRuntimeInstance> instanceSet = instanceMap.computeIfAbsent(baseElement, k -> new HashSet<>());
            return instanceSet;
        }

        public Set<BPMNRuntimeInstance> getEnclosingInstances(InteractionNode interactionNode) {
            if (!(interactionNode instanceof BaseElement)) {
                toolKit.log(this, interactionNode, "Unexpected instance of interactionNode, not a BaseElement");
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
                toolKit.log(this, interactionNode, "No enclosing instance found");
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
}
