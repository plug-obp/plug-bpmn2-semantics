package plug.bpmn2.dsim.tools;

import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;

import java.util.Collection;

class ElementsTranslate {

    static private BPMNRuntimeInstance get(Collection<BPMNRuntimeInstance> sourceCollection,
                                    Collection<BPMNRuntimeInstance> targetCollection,
                                    BPMNRuntimeInstance scope) {
        for (BPMNRuntimeInstance sourceInstance : sourceCollection) {
            for (BPMNRuntimeInstance targetInstance : targetCollection) {
                if (sourceInstance.equals(targetInstance)) {
                    if (sourceInstance == scope) {
                        return targetInstance;
                    } else {
                        BPMNRuntimeInstance result = get(
                                sourceInstance.getChildInstanceSet(),
                                targetInstance.getChildInstanceSet(),
                                scope
                        );
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }

    static public BPMNRuntimeInstance get(BPMNRuntimeState source,
                                   BPMNRuntimeState target,
                                   BPMNRuntimeInstance scope) {
        return get(source.getRootInstances(), target.getRootInstances(), scope);
    }

}
