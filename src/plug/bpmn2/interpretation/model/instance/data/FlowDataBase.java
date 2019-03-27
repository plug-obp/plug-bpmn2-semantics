package plug.bpmn2.interpretation.model.instance.data;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.Objects;

abstract class FlowDataBase implements FlowData {

    private final BPMNRuntimeInstance sourceParent;
    private final BPMNRuntimeInstance targetParent;

    public FlowDataBase(BPMNRuntimeInstance sourceParent,
                        BPMNRuntimeInstance targetParent) {
        this.sourceParent = sourceParent;
        this.targetParent = targetParent;
    }

    @Override
    public BPMNRuntimeInstance getSourceParent() {
        return sourceParent;
    }

    @Override
    public BPMNRuntimeInstance getTargetParent() {
        return targetParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowDataBase that = (FlowDataBase) o;
        return Objects.equals(sourceParent, that.sourceParent) &&
                Objects.equals(targetParent, that.targetParent) &&
                Objects.equals(getBaseElement(), that.getBaseElement()) &&
                Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBaseElement(), sourceParent, targetParent, getData());
    }

}
