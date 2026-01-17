package plug.bpmn2.semantics.state.instance;

import org.eclipse.bpmn2.Task;

public interface TaskInstance extends ActivityInstance {

    @Override
    Task getBaseElement();

}
