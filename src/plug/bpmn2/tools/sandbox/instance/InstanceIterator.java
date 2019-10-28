package plug.bpmn2.tools.sandbox.instance;

import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * IMPORTANT: Will iterate several times the same instance if it has multiple parents.
 */
public class InstanceIterator implements Iterator<BPMNRuntimeInstance> {

    private final LinkedList<BPMNRuntimeInstance> parentList = new LinkedList<>();
    private BPMNRuntimeInstance nextInstance;
    private Iterator<BPMNRuntimeInstance> currentChildIterator;

    public InstanceIterator(BPMNModelRuntimeState state) {
        currentChildIterator = state.getRootInstances().iterator();
        updateNext();
    }

    private boolean pickNextChild() {
        if (currentChildIterator.hasNext()) {
            nextInstance = currentChildIterator.next();
            parentList.addAll(nextInstance.getChildInstanceSet());
            return true;
        }
        return false;
    }

    private void updateNext() {
        if (pickNextChild()) return;
        if (!parentList.isEmpty()) {
            nextInstance = parentList.removeFirst();
            currentChildIterator = nextInstance.getChildInstanceSet().iterator();
        }
        nextInstance = null;
    }

    @Override
    public boolean hasNext() {
        return nextInstance != null;
    }

    @Override
    public BPMNRuntimeInstance next() {
        BPMNRuntimeInstance result = nextInstance;
        updateNext();
        return result;
    }

}
