package plug.bpmn2.tools.walker;

import plug.bpmn2.interpretation.model.BPMNInstanceVisitor;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;

public class BPMNRuntimeInstanceWalker {

    private final BPMNInstanceAspectHandler aspectHandler;
    private final BPMNInstanceVisitor leafVisitor;

    private final Set<BPMNRuntimeInstance> walkedInstanceSet;
    private final LinkedList<BPMNRuntimeInstance> toWalkInstanceList;

    public BPMNRuntimeInstanceWalker(
            BPMNInstanceAspectHandler aspectHandler,
            BPMNInstanceVisitor leafVisitor
    ) {
        this.aspectHandler = aspectHandler;
        this.leafVisitor = leafVisitor;
        this.walkedInstanceSet = new HashSet<>();
        this.toWalkInstanceList = new LinkedList<>();
    }

    public BPMNRuntimeInstanceWalker(BPMNInstanceAspectHandler aspectHandler) {
        this(aspectHandler, new BPMNInstanceVisitor() {});
    }

    public BPMNRuntimeInstanceWalker(BPMNInstanceVisitor leafVisitor) {
        this(new BPMNInstanceAspectHandler() {}, leafVisitor);
    }

    public BPMNRuntimeInstanceWalker(Consumer<BPMNRuntimeInstance> doEach) {
        this(new SimpleInstanceAspectHandler(doEach));
    }

    public void walkInstanceTree(BPMNRuntimeInstance instance) {
        walkedInstanceSet.clear();
        toWalkInstanceList.clear();
        toWalkInstanceList.add(instance);
        while (!toWalkInstanceList.isEmpty()) {
            BPMNRuntimeInstance nextInstance = toWalkInstanceList.removeFirst();
            BPMNInstanceAspectHandler.handle(aspectHandler, nextInstance);
            nextInstance.acceptInstanceVisitor(leafVisitor);
            for (BPMNRuntimeInstance childInstance : nextInstance.getChildInstanceSet()) {
                if (walkedInstanceSet.add(childInstance)) {
                    toWalkInstanceList.add(childInstance);
                }
            }
        }
    }
}