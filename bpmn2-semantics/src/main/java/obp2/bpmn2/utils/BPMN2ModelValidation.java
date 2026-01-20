package obp2.bpmn2.utils;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BPMN2ModelValidation {

    public static boolean nodesAreMissingFlows(Set<Process> rootProcesses) {
        for (Process rootProcess : rootProcesses) {
            for (FlowElement flowElement : rootProcess.getFlowElements()) {
                if (flowElement instanceof FlowNode) {
                    FlowNode flowNode = (FlowNode) flowElement;
                    if (!flowNode.getIncoming().isEmpty()) return false;
                    if (!flowNode.getOutgoing().isEmpty()) return false;
                }
            }
        }
        return true;
    }

    public static void addMissingFlows(Set<Process> rootProcesses) {
        System.err.println("Model is missing flow nodes' explicit flow data");
        Set<Process> knownProcesses = new HashSet<>(rootProcesses);
        List<Process> toSeeProcesses = new ArrayList<>(rootProcesses);
        while (!toSeeProcesses.isEmpty()) {
            Process currentProcess = toSeeProcesses.remove(toSeeProcesses.size() - 1);
            iterateFlowElements:
            for (FlowElement flowElement : currentProcess.getFlowElements()) {
                if (flowElement instanceof CallActivity) {
                    CallActivity callActivity = (CallActivity) flowElement;
                    CallableElement callableElement = callActivity.getCalledElementRef();
                    if (callableElement instanceof Process) {
                        Process discoveredProcess = (Process) callableElement;
                        if (knownProcesses.add(discoveredProcess)) toSeeProcesses.add(discoveredProcess);
                    }
                    continue iterateFlowElements;
                }
                else if (!(flowElement instanceof SequenceFlow)) continue iterateFlowElements;
                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                FlowNode source = sequenceFlow.getSourceRef();
                if (source != null) source.getOutgoing().add(sequenceFlow);
                FlowNode target = sequenceFlow.getTargetRef();
                if (target != null) target.getIncoming().add(sequenceFlow);
            }
        }
        System.err.println("Added incoming and outgoing data to flow nodes");
    }

}
