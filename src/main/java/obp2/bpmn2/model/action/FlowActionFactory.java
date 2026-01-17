package obp2.bpmn2.model.action;

import obp2.bpmn2.model.signal.SignalData;
import obp2.bpmn2.model.signal.SignalReferences;
import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.utils.BPMN2TokenUtils;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.*;

class FlowActionFactory {

    private final TokenPool tokenPool;
    private final SignalData signalData;
    private final LinkedList<CallActivity> callStack;

    public FlowActionFactory(TokenPool tokenPool, SignalData signalData) {
        this.tokenPool = tokenPool;
        this.signalData = signalData;
        this.callStack = new LinkedList<>();
    }

    public void enterCallActivity(CallActivity callActivity) {
        callStack.addLast(callActivity);
    }

    public void exitCallActivity() {
        callStack.removeLast();
    }

    private int[] getConnectedTokens(FlowNode flowNode, boolean incoming) {
        Collection<SequenceFlow> sequenceFlows = incoming ? flowNode.getIncoming() : flowNode.getOutgoing();
        List<Integer> tokenIdList = new ArrayList<>();
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            int outTokenId = tokenPool.getTokenId(callStack, sequenceFlow);
            tokenIdList.add(outTokenId);
        }
        return BPMN2TokenUtils.toArray(tokenIdList);
    }
    private int[] getOutgoingTokens(FlowNode flowNode) {
        return getConnectedTokens(flowNode, false);
    }

    private int[] getIncomingTokens(FlowNode flowNode) {
        return getConnectedTokens(flowNode, true);
    }

    private List<Integer> getInitialTokens(Process process) {
        List<Integer> tokenList = new ArrayList<>();
        for (FlowElement flowElement : process.getFlowElements()) {
            if (!(flowElement instanceof StartEvent)) continue;
            StartEvent startEvent = (StartEvent) flowElement;
            int[] outTokenIds = getOutgoingTokens(startEvent);
            for (int token : outTokenIds) tokenList.add(token);
        }
        return tokenList;
    }

    public BPMN2FlowAction getInitialisation(Collection<Process> rootProcesses) {
        BPMN2FlowAction initialAction = new BPMN2FlowAction(BPMN2FlowAction.Type.INITIALISATION);
        initialAction.setFlowNode(null);
        List<Integer> initialTokens = new ArrayList<>();
        for (Process process : rootProcesses) initialTokens.addAll(getInitialTokens(process));
        initialAction.setProducedTokens(initialTokens);
        return initialAction;
    }
    public Collection<BPMN2FlowAction> getTaskActions(FlowNode task) {
        int nodeTokenId = tokenPool.getTokenId(callStack, task);
        if (nodeTokenId < 0) {
            throw new IllegalStateException("Didn't find any token matching task & callStack");
        }
        int[] nodeTokenIdArray = {nodeTokenId};
        List<BPMN2FlowAction> flowActionList = new ArrayList<>();
        // For each incoming sequence flow, create a matching start task action
        for (SequenceFlow sequenceFlow : task.getIncoming()) {
            BPMN2FlowAction startTaskAction = new BPMN2FlowAction(BPMN2FlowAction.Type.START_TASK);
            startTaskAction.setFlowNode(task);
            startTaskAction.setMandatoryTokens(tokenPool.getTokenId(callStack, sequenceFlow));
            startTaskAction.setProducedTokens(nodeTokenIdArray);
            flowActionList.add(startTaskAction);
        }
        // Build the end task action
        BPMN2FlowAction endTaskAction = new BPMN2FlowAction(BPMN2FlowAction.Type.END_TASK);
        endTaskAction.setFlowNode(task);
        endTaskAction.setMandatoryTokens(nodeTokenIdArray);
        endTaskAction.setProducedTokens(getOutgoingTokens(task));
        flowActionList.add(endTaskAction);
        return flowActionList;
    }

    public Collection<BPMN2FlowAction> getCallActivityActions(CallActivity callActivity) {
        List<BPMN2FlowAction> flowActionList = new ArrayList<>();
        // TODO Raise a warning if OPEN_PROCESS is blocked because of excluded tokens
        int activityTokenId = tokenPool.getTokenId(callStack, callActivity);
        // Open process action(s)
        for (SequenceFlow sequenceFlow : callActivity.getIncoming()) {
            BPMN2FlowAction openAction = new BPMN2FlowAction(BPMN2FlowAction.Type.OPEN_PROCESS);
            openAction.setFlowNode(callActivity);
            openAction.setMandatoryTokens(tokenPool.getTokenId(callStack, sequenceFlow));
            List<Integer> producedTokens = new ArrayList<>();
            producedTokens.add(activityTokenId);
            if (callActivity.getCalledElementRef() instanceof Process) {
                Process calledProcess = (Process) callActivity.getCalledElementRef();
                enterCallActivity(callActivity);
                producedTokens.addAll(getInitialTokens(calledProcess));
                openAction.setExcludedTokens(tokenPool.getTokenIds(callStack));
                exitCallActivity();
            }
            openAction.setProducedTokens(producedTokens);
            flowActionList.add(openAction);
        }
        // Close process action
        BPMN2FlowAction closeAction = new BPMN2FlowAction(BPMN2FlowAction.Type.CLOSE_PROCESS);
        closeAction.setFlowNode(callActivity);
        closeAction.setMandatoryTokens(activityTokenId);
        closeAction.setProducedTokens(getOutgoingTokens(callActivity));
        if (callActivity.getCalledElementRef() instanceof Process) {
            enterCallActivity(callActivity);
            closeAction.setExcludedTokens(tokenPool.getTokenIds(callStack));
            exitCallActivity();
        }
        flowActionList.add(closeAction);
        return flowActionList;
    }

    public Collection<BPMN2FlowAction> getEndEventActions(EndEvent endEvent) {
        List<BPMN2FlowAction> flowActionList = new ArrayList<>();
        for (SequenceFlow inc : endEvent.getIncoming()) {
            BPMN2FlowAction endEventAction = new BPMN2FlowAction(BPMN2FlowAction.Type.END_EVENT);
            endEventAction.setFlowNode(endEvent);
            endEventAction.setMandatoryTokens(tokenPool.getTokenId(callStack, inc));
            flowActionList.add(endEventAction);
        }
        return flowActionList;
    }

    public List<BPMN2FlowAction> getThrowSignalAction(IntermediateThrowEvent throwEvent) {
        Object signal = signalData.getSignal(throwEvent);
        CatchEvent[] catchEvents = signalData.getCatchEvents(signal);
        List<Integer> optionalTokenList = new ArrayList<>();
        Map<Integer, int[]> optionalProducedTokenMap = new HashMap<>();
        for (CatchEvent catchEvent : catchEvents) {
            for (SequenceFlow incoming : catchEvent.getIncoming()) {
                for (int tokenId : tokenPool.getTokenIds(incoming)) {
                    optionalTokenList.add(tokenId);
                    List<CallActivity> incomingCallStack = tokenPool.getToken(tokenId).getCallStack();
                    List<Integer> outgoingTokenList = new ArrayList<>();
                    for (SequenceFlow outgoing : catchEvent.getOutgoing()) {
                        int outgoingTokenId = tokenPool.getTokenId(incomingCallStack, outgoing);
                        if (outgoingTokenId < 0) throw new IllegalStateException("Could not find token id");
                        outgoingTokenList.add(outgoingTokenId);
                    }
                    optionalProducedTokenMap.put(tokenId, BPMN2TokenUtils.toArray(outgoingTokenList));
                }
            }
        }
        int[] optionalTokens = BPMN2TokenUtils.toArray(optionalTokenList);
        List<BPMN2FlowAction> throwActionList = new ArrayList<>();
        for (SequenceFlow inc : throwEvent.getIncoming()) {
            BPMN2FlowAction throwAction = new BPMN2FlowAction(BPMN2FlowAction.Type.THROW_SIGNAL);
            throwAction.setFlowNode(throwEvent);
            throwAction.setMandatoryTokens(tokenPool.getTokenId(callStack, inc));
            throwAction.setOptionalTokens(optionalTokens);
            throwAction.setProducedTokens(getOutgoingTokens(throwEvent));
            throwAction.setOptionalProducedTokenMap(optionalProducedTokenMap);
            throwActionList.add(throwAction);
        }
        return throwActionList;
    }

    public BPMN2FlowAction getParallelGatewayAction(FlowNode gateway) {
        BPMN2FlowAction action = new BPMN2FlowAction(BPMN2FlowAction.Type.TRAVERSE_PAR_GATEWAY);
        action.setFlowNode(gateway);
        action.setMandatoryTokens(getIncomingTokens(gateway));
        action.setProducedTokens(getOutgoingTokens(gateway));
        return action;
    }

    public Collection<BPMN2FlowAction> getExclusiveGatewayActions(ExclusiveGateway gateway) {
        if (gateway.getOutgoing().size() != 1) {
            throw new IllegalStateException("Unsupported: exclusive gateway with multiple outgoing sequence flows");
        }
        SequenceFlow out = gateway.getOutgoing().get(0);
        int outTokenId = tokenPool.getTokenId(callStack, out);
        List<BPMN2FlowAction> flowActionList = new ArrayList<>();
        for (SequenceFlow inc : gateway.getIncoming()) {
            BPMN2FlowAction action = new BPMN2FlowAction(BPMN2FlowAction.Type.TRAVERSE_EXC_GATEWAY);
            action.setFlowNode(gateway);
            action.setMandatoryTokens(tokenPool.getTokenId(callStack, inc));
            action.setProducedTokens(outTokenId);
            flowActionList.add(action);
        }
        return flowActionList;
    }

    public boolean doesNotHaveThrow(IntermediateCatchEvent catchEvent) {
        Object signal = signalData.getSignal(catchEvent);
        SignalReferences signalReferences = signalData.getSignalReferencesMap().get(signal);
        return signalReferences.getThrowEvents().length == 0;
    }

    public List<BPMN2FlowAction> getThrowLessCatchActions(IntermediateCatchEvent catchEvent) {
        SequenceFlow out = catchEvent.getOutgoing().get(0);
        List<BPMN2FlowAction> flowActionList = new ArrayList<>();
        for (SequenceFlow inc : catchEvent.getIncoming()) {
            BPMN2FlowAction action = new BPMN2FlowAction(BPMN2FlowAction.Type.TRAVERSE_THROW_LESS_CATCH);
            action.setFlowNode(catchEvent);
            action.setMandatoryTokens(tokenPool.getTokenId(callStack, inc));
            action.setProducedTokens(getOutgoingTokens(catchEvent));
            flowActionList.add(action);
        }
        return flowActionList;
    }

    public boolean calledProcessHasTokens(FlowNode flowNode) {
        CallActivity callActivity = (CallActivity) flowNode;
        enterCallActivity(callActivity);
        List<Integer> tokens = getInitialTokens((Process) callActivity.getCalledElementRef());
        boolean result = true;
        if (tokens.size() == 0) result = false;
        else if (tokens.size() > 0 && tokens.get(0) == -1) result = false;
        exitCallActivity();
        return result;
    }
}
