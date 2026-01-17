package obp2.bpmn2.model;

import obp2.bpmn2.model.token.Token;
import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessInstances {

    private final Map<List<CallActivity>, Process> callStackMap;

    public ProcessInstances(TokenPool tokenPool) {
        callStackMap = new HashMap<>();
        for (Token token : tokenPool.getTokenArray()) {
            BaseElement baseElement = token.getPlaceBaseElement();
            if (baseElement instanceof FlowElement) {
                FlowElement flowElement = (FlowElement) baseElement;
                FlowElementsContainer container = (FlowElementsContainer) baseElement.eContainer();
                if (container instanceof Process) {
                    callStackMap.put(token.getCallStack(), (Process) container);
                }
            }
        }
    }

    public Process getProcess(List<CallActivity> callStack) {
        return callStackMap.get(callStack);
    }

}
