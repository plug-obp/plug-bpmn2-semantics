package plug.bpmn2.tools.sandbox.transition;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.transition.BPMNAbstractTransition;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GlobalATS {

    private final Map<BaseElement, BaseElementATS> baseElementATSMap = new HashMap<>();

    public BaseElementATS getRuntimeScopeATS(BaseElement baseElement) {
        return baseElementATSMap.computeIfAbsent(
                baseElement,
                (be) -> new BaseElementATS(baseElement)
        );
    }

    public BaseElementATS getEmptyScopeATS() {
        return getRuntimeScopeATS(null);
    }

    public Iterator<BPMNAbstractTransition> iterator(BPMNModelRuntimeState state, BPMNRuntimeInstance runtimeScope) {
        BaseElement baseElement = runtimeScope == null ? null : runtimeScope.getBaseElement();
        return getRuntimeScopeATS(baseElement).iterator(state, runtimeScope);
    }

}
