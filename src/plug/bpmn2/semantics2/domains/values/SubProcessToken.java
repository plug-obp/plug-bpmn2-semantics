package plug.bpmn2.semantics2.domains.values;

import org.eclipse.bpmn2.SequenceFlow;
import plug.bpmn2.semantics2.domains.instances.SubProcessInstance;

public class SubProcessToken extends Token {
    SubProcessInstance subProcessInstance;
    SequenceFlow position;
}
