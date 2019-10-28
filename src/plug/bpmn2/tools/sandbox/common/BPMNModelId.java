package plug.bpmn2.tools.sandbox.common;

import org.eclipse.bpmn2.BaseElement;

import java.util.HashMap;
import java.util.Map;

public class BPMNModelId {

    private final Map<BaseElement, String> baseElementIdMap = new HashMap<>();
    private final Map<String, BaseElement> idBaseElementMap = new HashMap<>();
    private int nextNullId = 0;

    private String buildId(BaseElement baseElement) {
        String baseId = baseElement.getId();
        if (baseId == null) {
            baseId = "null_" + nextNullId;
            nextNullId += 1;
        }
        String className = baseElement.getClass().getSimpleName().replace("Impl", "");
        return baseId.contains(className) ? baseId : className + "_" + baseId;
    }

    public String get(BaseElement baseElement) {
        String result = baseElementIdMap.get(baseElement);
        if (result == null) {
            result = buildId(baseElement);
            baseElementIdMap.put(baseElement, result);
            idBaseElementMap.put(result, baseElement);
        }
        return result;
    }

    public BaseElement get(String id) {
        return idBaseElementMap.get(id);
    }

}
