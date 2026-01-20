package plug.bpmn2.semantics.transition;

import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RuntimeScope {

    private final LinkedList<Integer> path;
    private final String id;

    public RuntimeScope(BPMNRuntimeState state, BPMNRuntimeInstance instance) {
        id = instance.getBaseElement().getId();
        path = new LinkedList<>();
        BPMNRuntimeInstance previousParent = instance;
        BPMNRuntimeInstance parent = instance.getParent();
        while (parent != null) {
            int index = parent.getChildInstanceList().indexOf(previousParent);
            if (index == -1) {
                throw new IllegalStateException();
            }
            path.addFirst(index);
            previousParent = parent;
            parent = parent.getParent();
        }
        int index = state.getRootInstanceList().indexOf(previousParent);
        if (index == -1) {
            throw new IllegalStateException();
        }
        path.addFirst(index);
    }

    public BPMNRuntimeInstance getInstance(BPMNRuntimeState state) {
        if (path.size() == 0) return null;
        List<BPMNRuntimeInstance> currentFloor = state.getRootInstanceList();
        int currentIndex = path.get(path.get(0));
        BPMNRuntimeInstance currentInstance = currentFloor.get(currentIndex);
        for (int i = 1; i < path.size(); i++) { ;
            currentFloor = currentInstance.getChildInstanceList();
            currentIndex = path.get(i);
            currentInstance = currentFloor.get(currentIndex);
        }
        return currentInstance;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuntimeScope that = (RuntimeScope) o;
        return path.equals(that.path) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, id);
    }
}
