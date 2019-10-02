package plug.bpmn2.tools.sandbox.diagnosis;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.*;

public class BPMN2ContainmentDiagnosis {

    private Set<EObject> modelObjectSet = new HashSet<>();
    private Set<EObject> viewObjectSet = new HashSet<>();

    private Map<EObject, Set<EObject>> containedMap = new HashMap<>();
    private Map<EObject, Set<EObject>> containersMap = new HashMap<>();

    private List<Set<EObject>> containmentFloorList = new LinkedList<>();
    private Map<EObject, Integer> containmentDepthMap = new HashMap<>();

    public void populate(EObject rootObject) {
        modelObjectSet.clear();
        viewObjectSet.clear();
        containedMap.clear();
        containersMap.clear();
        containmentFloorList.clear();
        containmentDepthMap.clear();
        new InternalSwitch().doSwitch(rootObject);
        fillContainmentFloorList();
        computeContainmentDepthMap();
    }

    private void fillContainmentFloorList() {
        Set<EObject> rootFloorSet = new HashSet<>();
        for (EObject object : modelObjectSet) {
            if (getContainers(object).size() == 0) {
                rootFloorSet.add(object);
            }
        }
        Set<EObject> previousFloorSet = rootFloorSet;
        while (!previousFloorSet.isEmpty()) {
            Set<EObject> nextFloorSet = new HashSet<>();
            for (EObject object : previousFloorSet) {
                for (EObject contained : getContained(object)) {
                    if (modelObjectSet.contains(contained)) {
                        nextFloorSet.add(contained);
                    }
                }
            }
            containmentFloorList.add(previousFloorSet);
            previousFloorSet = nextFloorSet;
        }
    }

    private void computeContainmentDepthMap() {
        int depth = 0;
        for (Set<EObject> floorSet : containmentFloorList) {
            for (EObject object : floorSet) {
                containmentDepthMap.put(object, depth);
            }
            depth = depth + 1;
        }
    }

    public Set<EObject> getModelObjectSet() {
        return modelObjectSet;
    }

    public Set<EObject> getViewObjectSet() {
        return viewObjectSet;
    }

    public Set<EObject> getContained(EObject object) {
        return containedMap.get(object);
    }

    public Set<EObject> getContainers(EObject object) {
        return containersMap.computeIfAbsent(
                object,
                (e) -> new HashSet<>()
        );
    }

    class InternalSwitch extends Bpmn2Switch<Object> {

        private final Object NON_NULL_TAG = 0;

        private void switchContained(EObject container) {
            Set<EObject> content = new HashSet<>(container.eContents());
            containedMap.put(container, content);
            for (EObject contained : content) {
                getContainers(contained).add(container);
                doSwitch(contained);
            }
        }

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            if (modelObjectSet.add(object)) {
                switchContained(object);
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseBaseElement(BaseElement object) {
            if (modelObjectSet.add(object)) {
                switchContained(object);
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object defaultCase(EObject object) {
            if (viewObjectSet.add(object)) {
                switchContained(object);
            }
            return NON_NULL_TAG;
        }
    }

}
