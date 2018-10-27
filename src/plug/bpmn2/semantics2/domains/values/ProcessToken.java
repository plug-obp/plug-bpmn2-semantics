package plug.bpmn2.semantics2.domains.values;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.semantics2.domains.instances.ProcessInstance;

import java.util.Collection;

public class ProcessToken extends Token {
    ProcessInstance processInstance;
    SequenceFlow position;

    public void setPosition(SequenceFlow position) {
        this.position = position;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
}
