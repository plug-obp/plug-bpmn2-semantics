package obp2.bpmn2.model.token;

import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TokenPoolUtils {

    static public TokenPool compute(Set<Process> rootProcesses, boolean includeCalledProcesses) {
        List<Token> tokenList = new LinkedList<>();
        for (Process process : rootProcesses) {
            List<CallActivity> callStack = Collections.emptyList();
            addTokens(tokenList, callStack, process, includeCalledProcesses);
        }
        TokenPool result = new TokenPool(tokenList);
        return result;
    }

    static public TokenPool compute(Set<Process> rootProcesses) {
        return compute(rootProcesses, true);
    }

    static public FlowNode getActionFlowNodeFrom(BaseElement placeBaseElement) {
        if (placeBaseElement instanceof Activity) return (FlowNode) placeBaseElement;
        if (placeBaseElement instanceof SequenceFlow) {
            return ((SequenceFlow) placeBaseElement).getTargetRef();
        }
        throw new IllegalStateException("Unsupported base element as place for tokens");
    }

    static private void addTokens(
            List<Token> tokenList, List<CallActivity> callStack, Process process,
            boolean includeCalledProcesses
    ) {
        for (FlowElement flowElement : process.getFlowElements()) {
            if (flowElement instanceof SequenceFlow) tokenList.add(new Token(callStack, flowElement));
            else if (flowElement instanceof CallActivity) {
                tokenList.add(new Token(callStack, flowElement));
                CallActivity callActivity = (CallActivity) flowElement;
                CallableElement calledElement = callActivity.getCalledElementRef();
                if (includeCalledProcesses && calledElement instanceof Process) {
                    Process calledProcess = (Process) calledElement;
                    checkCallCycle(callStack, calledProcess);
                    List<CallActivity> newCallStack = new LinkedList<>(callStack);
                    newCallStack.add(callActivity);
                    addTokens(tokenList, newCallStack, calledProcess, true);
                }
            } else if (flowElement instanceof Task) {
                tokenList.add(new Token(callStack, flowElement));
            }
        }
    }

    static private void checkCallCycle(List<CallActivity> callStack, Process calledProcess) {
        for (CallActivity callActivity : callStack) {
            Process owner = (Process) callActivity.eContainer();
            if (owner == calledProcess) throw new IllegalStateException("Call cycle detected and unsupported");
        }
    }

    static public String getReportString(TokenPool tokenPool) {
        StringBuilder report = new StringBuilder();
        report.append("TokenPool has ").append(tokenPool.getTokenArray().length).append(" tokens.");
        int maxCallDepth = 0;
        int nbSequenceFlow = 0;
        int nbTask = 0;
        int nbCallActivity = 0;
        for (Token token : tokenPool.getTokenArray()) {
            if (token.getCallStack().size() > maxCallDepth) maxCallDepth = token.getCallStack().size();
            if (token.getPlaceBaseElement() instanceof SequenceFlow) nbSequenceFlow += 1;
            else if (token.getPlaceBaseElement() instanceof Task) nbTask += 1;
            else if (token.getPlaceBaseElement() instanceof CallActivity) nbCallActivity += 1;
        }
        report.append("\n").append("Places: ")
                .append(nbSequenceFlow).append(" sequence flows, ")
                .append(nbTask).append(" tasks, ")
                .append(nbCallActivity).append(" call activities.");
        report.append("\n").append("Maximum call stack size: ").append(maxCallDepth).append(".");
        return report.toString();
    }

}
