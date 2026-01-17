package obp2.bpmn2.model.action;

import obp2.bpmn2.model.signal.SignalData;
import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FlowActionUtils {

    static public FlowActionPool compute(Set<Process> rootProcesses, TokenPool tokenPool, SignalData signalData) {
        FlowActionFactory factory = new FlowActionFactory(tokenPool, signalData);
        LinkedList<BPMN2FlowAction> flowActionList = new LinkedList<>();
        flowActionList.add(factory.getInitialisation(rootProcesses));
        for (Process rootProcess : rootProcesses) {
            addActions(factory, rootProcess, flowActionList);
        }
        return new FlowActionPool(flowActionList);
    }

    static private boolean isCallActivityWithProcess(FlowNode flowNode) {
        if (!(flowNode instanceof CallActivity)) return false;
        CallActivity callActivity = (CallActivity) flowNode;
        if (callActivity.getCalledElementRef() instanceof Process) return true;
        return false;
    }
    static private void addActions(
            FlowActionFactory factory, Process process, List<BPMN2FlowAction> flowActionList)
    {
        for (FlowElement flowElement : process.getFlowElements()) {
            if (!(flowElement instanceof FlowNode)) continue;
            FlowNode flowNode = (FlowNode) flowElement;
            if (flowNode instanceof StartEvent) {
                continue;
            }
            if (flowNode.getIncoming().size() == 0) {
                System.err.println(
                        BPMN2EmfUtils.getFlowElementName(flowNode) + " has no incoming sequence flows (ignored)"
                );
                continue;
            }
            if (flowNode instanceof EndEvent) {
                EndEvent endEvent = (EndEvent) flowNode;
                flowActionList.addAll(factory.getEndEventActions(endEvent));
                continue;
            }
            if (flowNode instanceof IntermediateThrowEvent) {
                IntermediateThrowEvent throwEvent = (IntermediateThrowEvent) flowNode;
                flowActionList.addAll(factory.getThrowSignalAction(throwEvent));
                continue;
            } if (flowNode instanceof IntermediateCatchEvent) {
                IntermediateCatchEvent catchEvent = (IntermediateCatchEvent) flowNode;
                if (factory.doesNotHaveThrow(catchEvent)) {
                    flowActionList.addAll(factory.getThrowLessCatchActions(catchEvent));
                }
                continue;
            }
            if (flowNode instanceof ParallelGateway) {
                ParallelGateway gateway = (ParallelGateway) flowNode;
                flowActionList.add(factory.getParallelGatewayAction(gateway));
                continue;
            }
            if (flowNode instanceof ExclusiveGateway) {
                ExclusiveGateway gateway = (ExclusiveGateway) flowNode;
                flowActionList.addAll(factory.getExclusiveGatewayActions(gateway));
                continue;
            }
            if (isCallActivityWithProcess(flowNode) && factory.calledProcessHasTokens(flowNode)) {
                CallActivity callActivity = (CallActivity) flowNode;
                flowActionList.addAll(factory.getCallActivityActions(callActivity));
                Process calledProcess = (Process) callActivity.getCalledElementRef();
                factory.enterCallActivity(callActivity);
                addActions(factory, calledProcess, flowActionList);
                factory.exitCallActivity();
                continue;
            }
            if (flowNode instanceof CallActivity || flowNode instanceof Task) {
                if (flowNode instanceof CallActivity && !isCallActivityWithProcess(flowNode)) {
                    System.err.println(
                            BPMN2EmfUtils.getFlowElementName(flowNode) + " has no callable process (handled as a task)"
                    );
                }
                flowActionList.addAll(factory.getTaskActions(flowNode));
                continue;
            }
            System.out.println("Unsupported so far (" + flowNode + ")");
        }
    }

    private static String getReportString(FlowActionPool flowActionPool, BPMN2FlowAction.Type type) {
        StringBuilder report = new StringBuilder();
        int noIncomingCount = 0;
        int noOutgoingCount = 0;
        int optionalCount = 0;
        int excludedCount = 0;
        for (BPMN2FlowAction flowAction : flowActionPool.getFlowActionArray()) {
            if (flowAction.getType() != type) continue;
            if (flowAction.getMandatoryTokens().length == 0) noIncomingCount++;
            if (flowAction.getProducedTokens().length == 0) noOutgoingCount++;
            if (flowAction.getOptionalTokens().length != 0) optionalCount++;
            if (flowAction.getExcludedTokens().length != 0) excludedCount++;
        }
        if (noIncomingCount > 0)
            report.append("    Has no mandatory tokens: ").append(noIncomingCount).append("\n");
        if (noOutgoingCount > 0)
            report.append("    Does not produce tokens: ").append(noOutgoingCount).append("\n");
        if (optionalCount > 0)
            report.append("    Has optional tokens: ").append(optionalCount).append("\n");
        if (excludedCount > 0)
            report.append("    Has excluded tokens: ").append(excludedCount).append("\n");
        return report.toString();
    }
    public static String getReportString(FlowActionPool flowActionPool) {
        StringBuilder report = new StringBuilder();
        int[] typeCounts = new int[BPMN2FlowAction.Type.values().length];
        int noIncomingCount = 0;
        int noOutgoingCount = 0;
        int optionalCount = 0;
        int excludedCount = 0;
        for (BPMN2FlowAction flowAction : flowActionPool.getFlowActionArray()) {
            typeCounts[flowAction.getType().ordinal()]++;
            if (flowAction.getMandatoryTokens().length == 0) noIncomingCount++;
            if (flowAction.getProducedTokens().length == 0) noOutgoingCount++;
            if (flowAction.getOptionalTokens().length != 0) optionalCount++;
            if (flowAction.getExcludedTokens().length != 0) excludedCount++;
        }
        for (BPMN2FlowAction.Type type : BPMN2FlowAction.Type.values()) {
            report.append(type).append(": ").append(typeCounts[type.ordinal()]).append("\n");
            report.append(getReportString(flowActionPool, type));
        }
        report.append("Has no mandatory tokens (total): ").append(noIncomingCount).append("\n")
                .append("Does not produce tokens (total): ").append(noOutgoingCount).append("\n")
                .append("Has optional tokens (total): ").append(optionalCount).append("\n")
                .append("Has excluded tokens (total): ").append(excludedCount);
        return report.toString();
    }
}
