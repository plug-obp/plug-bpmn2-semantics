package plug.bpmn2.interpretation.transition;

import org.eclipse.bpmn2.BaseElement;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.Set;

public interface BPMNRuntimeTransition<T extends BaseElement> {

    T owner();
    boolean guard(BPMNRuntimeInstance source);
    Set<BPMNRuntimeInstance> execute(BPMNRuntimeInstance source);

}
