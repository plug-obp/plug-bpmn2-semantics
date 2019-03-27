package plug.bpmn2.interpretation.model.instance.data;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.Objects;

abstract class FlowDataBase implements FlowData {

    private final BPMNRuntimeInstance parentRef;
    private final BPMNRuntimeInstance targetParent;

    public FlowDataBase(BPMNRuntimeInstance parentRef,
                        BPMNRuntimeInstance targetRef) {
        this.parentRef = parentRef;
        this.targetParent = targetRef;
    }

    @Override
    public BPMNRuntimeInstance getSourceRef() {
        return parentRef;
    }

    @Override
    public BPMNRuntimeInstance getTargetRef() {
        return targetParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowDataBase that = (FlowDataBase) o;
        return Objects.equals(parentRef, that.parentRef) &&
                Objects.equals(targetParent, that.targetParent) &&
                Objects.equals(getBaseElement(), that.getBaseElement()) &&
                Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBaseElement(), parentRef, targetParent, getData());
    }

}
