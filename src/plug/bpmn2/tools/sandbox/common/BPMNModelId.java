package plug.bpmn2.tools.sandbox.common;

import org.eclipse.bpmn2.BaseElement;

import java.util.HashMap;
import java.util.Map;

public class BPMNModelId {

    static public final String ROOT = "root";

    private final Map<BaseElement, String> baseElementIdMap = new HashMap<>();
    private final Map<String, BaseElement> idBaseElementMap = new HashMap<>();
    private int nextTieBreaker = 0;

    public BPMNModelId() {
        baseElementIdMap.put(null, ROOT);
        idBaseElementMap.put(ROOT, null);
    }

    private String buildId(BaseElement baseElement) {
        String result = baseElement.getId();
        if (result == null) {
            result = "nullId";
        } else {
            String className = baseElement.getClass().getSimpleName();
            className = className.replace("Impl", "");
            result = result.contains(className) ? result : className + "_" + result;
        }
        while (idBaseElementMap.containsKey(result)) {
            result += "_" + nextTieBreaker++;
        }
        return result;
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
