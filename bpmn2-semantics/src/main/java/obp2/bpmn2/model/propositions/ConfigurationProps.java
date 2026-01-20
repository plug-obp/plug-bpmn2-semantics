package obp2.bpmn2.model.propositions;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.token.Token;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.Process;

public class ConfigurationProps {

    private final BPMN2ProcessedModel model;
    private final BPMN2ExecutionState state;

    public ConfigurationProps(BPMN2ProcessedModel model, BPMN2ExecutionState state) {
        this.model = model;
        this.state = state;
    }

    public int tokensLength() {
        return state.tokens.length;
    }

    public boolean isTerminated() {
        return tokensLength() == 0;
    }

    public boolean hasActiveProcess(String processName) {
        for (int tokenId : state.tokens) {
            Token token = model.getTokenPool().getToken(tokenId);
            Process process = model.getProcess(token.getCallStack());
            if (process.getName().equals(processName)) return true;
        }
        return false;
    }

    public boolean hasActiveTask(String taskName) {
        for (int tokenId : state.tokens) {
            Token token = model.getTokenPool().getToken(tokenId);
            if (token.getPlaceBaseElement() instanceof FlowNode) {
                FlowNode task = (FlowNode) token.getPlaceBaseElement();
                if (taskName.equals(task.getName())) return true;
            }
        }
        return false;
    }

}
