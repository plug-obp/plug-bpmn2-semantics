package plug.bpmn2.tools.runtime.system;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.transition.BPMNAbstractTransition;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GlobalATS {

    private final BPMNModelHandler modelHandler;
    private final Map<BaseElement, BaseElementATS> baseElementATSMap = new HashMap<>();

    public GlobalATS(BPMNModelHandler modelHandler) {
        this.modelHandler = modelHandler;
    }

    public BaseElementATS getBaseElementATS(BaseElement baseElement) {
        return baseElementATSMap.computeIfAbsent(
                baseElement,
                (be) -> new BaseElementATS(modelHandler, be)
        );
    }

    public BaseElementATS getBaseElementATS(BaseElement baseElement, String id, String... path) {
        BaseElementATS result = baseElementATSMap.get(baseElement).getSubSystem(id);
        for (String nextId : path) {
            result = result.getSubSystem(nextId);
        }
        return result;
    }

    public BaseElementATS getEmptyScopeATS() {
        return getBaseElementATS(null);
    }

    public Iterator<BPMNAbstractTransition> iterator(BPMNRuntimeState state, BPMNRuntimeInstance runtimeScope) {
        BaseElement baseElement = runtimeScope == null ? null : runtimeScope.getBaseElement();
        return getBaseElementATS(baseElement).iterator(state, runtimeScope);
    }

}
