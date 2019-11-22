package plug.bpmn2.tools.runtime.system.ats;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.transition.BPMNAbstractTransition;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.common.BPMNModelId;
import plug.bpmn2.tools.runtime.system.TransitionGuard;

import java.util.*;

public class BaseElementATS {

    static private final String DEFAULT_ID = "DEFAULT_ID";

    private final BaseElementATS parent;
    private final String localId;
    private final String absoluteId;
    private final BaseElement baseElement;
    private final TransitionGuard guard;
    private final Set<BPMNAbstractTransition> transitionSet = new HashSet<>();
    private final Map<String, BaseElementATS> subSystemMap = new HashMap<>();

    private BaseElementATS(BaseElementATS parent, String localId, BaseElement baseElement, TransitionGuard guard) {
        this.parent = parent;
        this.localId = (localId != null ? localId : DEFAULT_ID);
        absoluteId = (parent != null ? parent.getAbsoluteId() + localId : localId);
        this.baseElement = baseElement;
        this.guard = (guard != null ? guard : TransitionGuard.TRUE);
    }

    BaseElementATS(BPMNModelHandler modelHandler, BaseElement baseElement) {
        this(
                null,
                baseElement == null ? BPMNModelId.ROOT : modelHandler.id.get(baseElement),
                baseElement,
                null
        );
    }

    public BaseElementATS getParent() {
        return parent;
    }

    public String getLocalId() {
        return localId;
    }

    public String getAbsoluteId() {
        return absoluteId;
    }

    public Collection<BaseElementATS> getSubSystems() {
        return subSystemMap.values();
    }

    public Collection<String> getSubSystemsIDs() {
        return subSystemMap.keySet();
    }

    public BaseElementATS getSubSystem(String id) {
        return subSystemMap.get(id);
    }

    public BaseElementATS newSubSystem(String localId, TransitionGuard guard) {
        BaseElementATS result = new BaseElementATS(this, localId, baseElement, guard);
        if (subSystemMap.containsKey(result.getLocalId())) {
            String message =
                    "Attempting to create a BaseElementATS with duplicate id: " +
                    result.getAbsoluteId();
            throw new IllegalArgumentException(message);
        }
        subSystemMap.put(result.getLocalId(), result);
        return result;
    }

    public boolean checkGuard(BPMNRuntimeState state, BPMNRuntimeInstance runtimeScope) {
        return guard.check(state, runtimeScope);
    }

    public BaseElement getBaseElement() {
        return baseElement;
    }

    public Set<BPMNAbstractTransition> getTransitionSet() {
        return transitionSet;
    }

    public Map<String, BaseElementATS> getSubSystemMap() {
        return subSystemMap;
    }

    public Iterator<BPMNAbstractTransition> iterator(BPMNRuntimeState state, BPMNRuntimeInstance runtimeScope) {
        if (!checkGuard(state, runtimeScope)) {
            return Collections.emptyIterator();
        }
        return new InternalIterator(state, runtimeScope);
    }

    private class InternalIterator implements Iterator<BPMNAbstractTransition> {

        private final BPMNRuntimeState state;
        private final BPMNRuntimeInstance runtimeScope;

        private Iterator<BPMNAbstractTransition> transitionIterator;
        private Iterator<BaseElementATS> subSystemIterator;

        private BPMNAbstractTransition nextTransition = null;

        private InternalIterator(BPMNRuntimeState state, BPMNRuntimeInstance runtimeScope) {
            this.state = state;
            this.runtimeScope = runtimeScope;
            transitionIterator = transitionSet.iterator();
            subSystemIterator = subSystemMap.values().iterator();
            updateNext();
        }

        private boolean nextLocalTransition() {
            if (!transitionIterator.hasNext()) {
                nextTransition = null;
                return false;
            }
            nextTransition = transitionIterator.next();
            return true;
        }

        private void updateNext() {
            if (nextLocalTransition()) return;
            while (subSystemIterator.hasNext()) {
                BaseElementATS nextSubSystem = subSystemIterator.next();
                transitionIterator = nextSubSystem.iterator(state, runtimeScope);
                if (nextLocalTransition()) break;
            }
        }

        @Override
        public boolean hasNext() {
            return nextTransition != null;
        }

        @Override
        public BPMNAbstractTransition next() {
            BPMNAbstractTransition result = nextTransition;
            updateNext();
            return result;
        }
    }

}
