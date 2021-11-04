package plug.bpmn2.model.analysis;

import java.util.*;

public abstract class AbstractDAGDiagnosis<T> {

    private Set<T> nodeSet = new HashSet<>();

    private Map<T, Set<T>> sourceMap = new HashMap<>();
    private Map<T, Set<T>> targetMap = new HashMap<>();

    public Set<T> getNodeSet() {
        return nodeSet;
    }

    public Set<T> getTargetSet(T object) {
        return targetMap.computeIfAbsent(
                object,
                (e) -> new HashSet<>());
    }

    public Set<T> getSourceSet(T object) {
        return sourceMap.computeIfAbsent(
                object,
                (e) -> new HashSet<>()
        );
    }

    abstract void fillNodeTargetSourceSets(T rootObject);

    private List<Set<T>> floorList = new LinkedList<>();
    private Map<Integer, Set<T>> depthNodeSetMap = new HashMap<>();
    private Map<T, Integer> nodeDepthMap = new HashMap<>();

    public Set<T> getFloorSet(int depth) {
        return depthNodeSetMap.get(depth);
    }

    public Integer getDepth(T node) {
        return nodeDepthMap.get(node);
    }

    public void populate(T rootObject) {
        nodeSet.clear();
        targetMap.clear();
        sourceMap.clear();
        floorList.clear();
        depthNodeSetMap.clear();
        nodeDepthMap.clear();
        fillNodeTargetSourceSets(rootObject);
        fillContainmentFloorList();
        computeDepthMap();
    }

    private void fillContainmentFloorList() {
        Set<T> rootFloorSet = new HashSet<>();
        for (T object : nodeSet) {
            if (getSourceSet(object).size() == 0) {
                rootFloorSet.add(object);
            }
        }
        Set<T> previousFloorSet = rootFloorSet;
        while (!previousFloorSet.isEmpty()) {
            depthNodeSetMap.put(floorList.size(), previousFloorSet);
            Set<T> nextFloorSet = new HashSet<>();
            for (T object : previousFloorSet) {
                for (T contained : getTargetSet(object)) {
                    if (nodeSet.contains(contained)) {
                        nextFloorSet.add(contained);
                    }
                }
            }
            floorList.add(previousFloorSet);
            previousFloorSet = nextFloorSet;
        }
    }

    private void computeDepthMap() {
        int depth = 0;
        for (Set<T> floorSet : floorList) {
            for (T object : floorSet) {
                nodeDepthMap.put(object, depth);
            }
            depth = depth + 1;
        }
    }

}