package obp2.bpmn2.plugin;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.model.token.Token;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import obp2.runtime.core.TreeItem;
import obp2.runtime.core.defaults.DefaultTreeProjector;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BPMN2View extends DefaultTreeProjector<BPMN2ExecutionState, BPMN2FlowAction, Void> {

    private final BPMN2ProcessedModel model;
    public BPMN2View(BPMN2ProcessedModel model) {
        this.model = model;
    }

    public TreeItem projectInstance(BPMN2ExecutionState configuration, List<CallActivity> callStack, String processName) {
        StringBuilder nameBuilder = new StringBuilder();
        boolean first = true;
        for (CallActivity callActivity : callStack) {
            if (!first) {
                nameBuilder.append(" -> ");
            } else {
                first = false;
            }
            nameBuilder.append("[")
                    .append(BPMN2EmfUtils.getFlowElementName(callActivity))
                    .append("]");
        }
        if (!first) nameBuilder.append(" : ");
        nameBuilder.append(processName);

        List<TreeItem> tokenNames = new ArrayList<>();
        for (int tokenId : configuration.tokens) {
            Token token = model.getTokenPool().getToken(tokenId);
            if (callStack.equals(token.getCallStack())) {
                BaseElement baseElement = token.getPlaceBaseElement();
                String tokenString = baseElement.getId();
                if (baseElement instanceof SequenceFlow) {
                    SequenceFlow sequenceFlow = (SequenceFlow) baseElement;
                    tokenString = "[" + BPMN2EmfUtils.getFlowElementName(sequenceFlow.getSourceRef())
                            + "] --> [" + BPMN2EmfUtils.getFlowElementName(sequenceFlow.getTargetRef())
                            + "] : [" + BPMN2EmfUtils.getFlowElementName(sequenceFlow) + "]";
                    if (sequenceFlow.getTargetRef() instanceof CatchEvent) {
                        tokenString = "(C) " + tokenString;
                    } else if (sequenceFlow.getTargetRef() instanceof ParallelGateway) {
                        ParallelGateway parallelGateway = (ParallelGateway) sequenceFlow.getTargetRef();
                        if (parallelGateway.getIncoming().size() > 1)
                            tokenString = "(J) " + tokenString;
                    }
                } else if (baseElement instanceof FlowElement) {
                    FlowElement flowElement = (FlowNode) baseElement;
                    tokenString = BPMN2EmfUtils.getFlowElementName(flowElement);
                    if (flowElement instanceof CallActivity) {
                        tokenString = "(P) " + tokenString;
                    }
                }
                tokenNames.add(new TreeItem(tokenString));
            }
        }
        nameBuilder.append(" (").append(tokenNames.size()).append(" token");
        if (tokenNames.size() > 1) nameBuilder.append("s");
        nameBuilder.append(")");
        return new TreeItem(nameBuilder.toString(), tokenNames);
    }

    public TreeItem projectTokens(BPMN2ExecutionState configuration) {
        List<TreeItem> instanceList = new ArrayList<>();

        return new TreeItem("Tokens", instanceList);
    }

    @Override
    public TreeItem projectConfiguration(BPMN2ExecutionState configuration) {
        String name = "BPMN2 Configuration";
        List<TreeItem> featureList = new ArrayList<>();

        Set<List<CallActivity>> instances = new HashSet<>();
        for (int tokenId : configuration.tokens) {
            Token token = model.getTokenPool().getToken(tokenId);
            instances.add(token.getCallStack());
        }

        featureList.add(new TreeItem(
                instances.size() + " process" + (instances.size() > 1 ? "es" : "") + " & " +
                configuration.tokens.length + " token" + (configuration.tokens.length > 1 ? "s" : "")
        ));

        for (List<CallActivity> callStack : instances) {
            Process process = model.getProcess(callStack);
            String processName = process.getName() == null ? "AnonymousProcess" : process.getName();
            featureList.add(projectInstance(
                    configuration,
                    callStack,
                    processName
            ));
        }

        return new TreeItem(name, featureList);
    }

    @Override
    public TreeItem projectFireable(BPMN2FlowAction action) {
        String nodeName = action.getFlowNode() == null ? " N/A"
                : " " + BPMN2EmfUtils.getFlowElementName(action.getFlowNode());
        TreeItem treeItem = new TreeItem(action.getType().toString() + nodeName);
        return treeItem;
    }

    @Override
    public TreeItem projectPayload(Void output) {
        return TreeItem.empty;
    }

}
