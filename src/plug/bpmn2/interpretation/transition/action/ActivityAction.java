package plug.bpmn2.interpretation.transition.action;

import org.eclipse.bpmn2.Activity;

public interface ActivityAction extends AtomicAction{

    @Override
    Activity scope();

}
