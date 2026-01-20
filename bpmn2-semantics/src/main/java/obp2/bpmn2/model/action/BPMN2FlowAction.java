package obp2.bpmn2.model.action;

import org.eclipse.bpmn2.FlowNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BPMN2FlowAction {

    public enum Type {
        INITIALISATION,
        START_TASK,
        END_TASK,
        OPEN_PROCESS,
        CLOSE_PROCESS,
        TRAVERSE_PAR_GATEWAY,
        TRAVERSE_EXC_GATEWAY,
        TRAVERSE_THROW_LESS_CATCH,
        THROW_SIGNAL,
        END_EVENT
    }

    private int id;
    private FlowNode flowNode;
    final private Type type;
    private int[] mandatoryTokens;
    private int[] producedTokens;
    private int[] excludedTokens;
    private int[] optionalTokens;
    private Map<Integer, int[]> optionalProducedTokenMap;

    public BPMN2FlowAction(Type type) {
        id = -1;
        flowNode = null;
        this.type = type;
        mandatoryTokens = new int[0];
        producedTokens = new int[0];
        excludedTokens = new int[0];
        optionalTokens = new int[0];
        // TODO refactor to int[][] (if need performances)
        optionalProducedTokenMap = new HashMap<>();
    }

    public FlowNode getFlowNode() {
        return flowNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFlowNode(FlowNode flowNode) {
        this.flowNode = flowNode;
    }

    public Type getType() {
        return type;
    }

    public int[] getMandatoryTokens() {
        return mandatoryTokens;
    }

    public void setMandatoryTokens(int[] mandatoryTokens) {
        this.mandatoryTokens = mandatoryTokens;
    }

    public void setMandatoryTokens(int singleToken) {
        this.mandatoryTokens = new int[] {singleToken};
    }

    public int[] getExcludedTokens() {
        return excludedTokens;
    }

    public void setExcludedTokens(int[] excludedTokens) {
        this.excludedTokens = excludedTokens;
    }

    public int[] getOptionalTokens() {
        return optionalTokens;
    }

    public void setOptionalTokens(int[] optionalTokens) {
        this.optionalTokens = optionalTokens;
    }

    public int[] getProducedTokens() {
        return producedTokens;
    }

    public void setProducedTokens(int[] producedTokens) {
        this.producedTokens = producedTokens;
    }

    public void setProducedTokens(int singleToken) {
        this.producedTokens = new int[] {singleToken};
    }

    public void setProducedTokens(List<Integer> tokensAsList) {
        producedTokens = new int[tokensAsList.size()];
        for (int i = 0; i < producedTokens.length; i++) producedTokens[i] = tokensAsList.get(i);
    }

    public int[] getProducedTokens(int optionalToken) {
        return optionalProducedTokenMap.get(optionalToken);
    }

    public void setOptionalProducedTokenMap(Map<Integer, int[]> optionalProducedTokenMap) {
        this.optionalProducedTokenMap = optionalProducedTokenMap;
    }

}
