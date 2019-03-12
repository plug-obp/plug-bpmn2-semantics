package plug.bpmn2.interpretation.model.instance;

import org.eclipse.bpmn2.Task;

public interface TaskInstance extends ActivityInstance {

    @Override
    Task getBaseElement();

}
