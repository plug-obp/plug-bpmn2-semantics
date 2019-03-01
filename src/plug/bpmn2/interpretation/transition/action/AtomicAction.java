package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.BaseElement;

public interface AtomicAction extends BPMNRuntimeAction {

    BaseElement scope();

}
