package obp2.bpmn2.model.signal;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.*;

public class SignalDataUtils {

    static public SignalData compute(Set<Process> rootProcesses, SignalIdStrategy signalIdStrategy) {
        Map<Object, List<ThrowEvent>> throwMap = new HashMap<>();
        Map<Object, List<CatchEvent>> catchMap = new HashMap<>();
        LinkedList<Process> toSeeProcesses = new LinkedList<>(rootProcesses);
        Set<Process> visitedProcesses = new HashSet<>(toSeeProcesses);
        while (!toSeeProcesses.isEmpty()) {
            Process process = toSeeProcesses.removeFirst();
            for (FlowElement flowElement : process.getFlowElements()) {
                if (flowElement instanceof IntermediateThrowEvent) {
                    ThrowEvent event = (ThrowEvent) flowElement;
                    Object signal = signalIdStrategy.getSignalId(event);
                    throwMap.computeIfAbsent(signal, s -> new ArrayList<>()).add(event);
                } else if (flowElement instanceof IntermediateCatchEvent) {
                    CatchEvent event = (CatchEvent) flowElement;
                    Object signal = signalIdStrategy.getSignalId(event);
                    catchMap.computeIfAbsent(signal, s -> new ArrayList<>()).add(event);
                } else if (flowElement instanceof CallActivity) {
                    CallActivity callActivity = (CallActivity) flowElement;
                    if (callActivity.getCalledElementRef() instanceof Process) {
                        Process calledProcess = (Process) callActivity.getCalledElementRef();
                        if (visitedProcesses.add(calledProcess)) toSeeProcesses.addLast(calledProcess);
                    }
                }
            }
        }
        return buildSignalData(signalIdStrategy, throwMap, catchMap);
    }
    static private SignalData buildSignalData(
            SignalIdStrategy signalIdStrategy,
            Map<Object, List<ThrowEvent>> throwMap,
            Map<Object, List<CatchEvent>> catchMap
    ) {
        Set<Object> signalSet = new HashSet<>();
        signalSet.addAll(throwMap.keySet());
        signalSet.addAll(catchMap.keySet());
        SignalData signalData = new SignalData(signalIdStrategy);
        for (Object signal : signalSet) {
            if (signal == null) continue;
            List<ThrowEvent> throwEventList = throwMap.computeIfAbsent(signal, s -> new ArrayList<>());
            ThrowEvent[] throwEventArray = new ThrowEvent[throwEventList.size()];
            for (int i = 0; i < throwEventArray.length; i++) throwEventArray[i] = throwEventList.get(i);
            List<CatchEvent> catchEventList = catchMap.computeIfAbsent(signal, s -> new ArrayList<>());
            CatchEvent[] catchEventArray = new CatchEvent[catchEventList.size()];
            for (int i = 0; i < catchEventArray.length; i++) catchEventArray[i] = catchEventList.get(i);
            SignalReferences signalReferences = new SignalReferences(signal, throwEventArray, catchEventArray);
            signalData.getSignalReferencesMap().put(signal, signalReferences);
        }
        return signalData;
    }

    static public String getReportString(SignalData signalData) {
        StringBuilder report = new StringBuilder();
        report.append("Model has ")
                .append(signalData.getSignalReferencesMap().keySet().size())
                .append(" different signals");
        int maxThrow = 0;
        int maxCatch = 0;
        int noThrow = 0;
        int noCatch = 0;
        for (SignalReferences signalReferences : signalData.getSignalReferencesMap().values()) {
            int nbThrow = signalReferences.getThrowEvents().length;
            int nbCatch = signalReferences.getCatchEvents().length;
            if (nbThrow > maxThrow) maxThrow = nbThrow;
            if (nbCatch > maxCatch) maxCatch = nbCatch;
            if (nbThrow == 0) noThrow += 1;
            if (nbCatch == 0) noCatch += 1;
        }
        report.append("\n").append("Max number of throws: ").append(maxThrow)
            .append("\n").append("Max number of catchs: ").append(maxCatch)
            .append("\n").append("Signals with no throw: ").append(noThrow)
            .append("\n").append("Signals with no catch: ").append(noCatch);
        return report.toString();
    }

}
