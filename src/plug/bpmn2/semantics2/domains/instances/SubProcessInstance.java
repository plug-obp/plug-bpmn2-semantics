package plug.bpmn2.semantics2.domains.instances;

import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.SubProcess;
import plug.bpmn2.semantics2.domains.values.Token;
import plug.bpmn2.semantics2.domains.values.Value;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class SubProcessInstance extends ActivityInstance {
    //the tokens traverse the sequence flow
    //a token is similar to the process counter in sequential programming languages
    //it represents the place in the process where the control is at a given moment
    final List<Token> tokens = new ArrayList<>();

    Map<DataObject, Value> dataObject2value = new IdentityHashMap<>();
}
