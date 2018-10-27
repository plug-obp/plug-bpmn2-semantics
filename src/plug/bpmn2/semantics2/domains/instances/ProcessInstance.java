package plug.bpmn2.semantics2.domains.instances;

import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.Process;
import plug.bpmn2.semantics2.domains.values.ProcessToken;
import plug.bpmn2.semantics2.domains.values.Token;
import plug.bpmn2.semantics2.domains.values.Value;

import java.util.*;

/*
* A process value represents a process instance
* */
public class ProcessInstance extends RootElementInstance {
    Process process;
    //the tokens traverse the sequence flow
    //a token is similar to the process counter in sequential programming languages
    //it represents the place in the process where the control is at a given moment
    final Set<Token> tokens = new HashSet<>();

    Map<DataObject, Value> dataObject2value = new IdentityHashMap<>();

    public ProcessInstance(Process process) {
        this.process = process;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public void addAll(Set<Token> tokenSet) {
        for (Token token : tokenSet) {
            if (token instanceof ProcessToken) {
                ((ProcessToken)token).setProcessInstance(this);
            }
        }
        tokens.addAll(tokenSet);
    }

    @Override
    public Collection<Object> getExecutableSteps(StructuralInstance configuration) {
        if (tokens.isEmpty()) {
            return Collections.emptyList();
        }
        CollaborationInstance conf = (CollaborationInstance) configuration;
        List<Object> steps = new ArrayList<>();

        for (Token token : tokens) {
            steps.addAll(token.getExecutableSteps());
        }

        return steps;
    }
}

