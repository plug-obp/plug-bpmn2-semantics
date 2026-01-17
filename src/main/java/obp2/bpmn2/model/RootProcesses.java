package obp2.bpmn2.model;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;

import java.util.*;
import java.util.function.Predicate;

public class RootProcesses {

    static public RootElement getRootElement(DocumentRoot documentRoot) {
        RootElement result = documentRoot.getRootElement();
        if (result == null) {
            if (documentRoot.getCollaboration() != null) return documentRoot.getCollaboration();
            List<Process> rootProcesses = new ArrayList<>();
            for (RootElement rootElement : documentRoot.getDefinitions().getRootElements()) {
                if (rootElement instanceof Collaboration) return rootElement;
                if (rootElement instanceof Process) rootProcesses.add((Process) rootElement);
            }
            if (rootProcesses.size() == 1) return rootProcesses.get(0);
            throw new IllegalStateException("Document has null root element and no collaboration was found");
        }
        return result;
    }

    static public Set<Process> compute(DocumentRoot documentRoot) {
        Set<Process> result = new HashSet<>();
        RootElement rootElement = getRootElement(documentRoot);
        if (rootElement instanceof Process) result.add((Process) rootElement);
        else if (rootElement instanceof Collaboration) {
            for (Participant participant : ((Collaboration) rootElement).getParticipants()) {
                result.add(participant.getProcessRef());
            }
        } else {
            throw new IllegalStateException("Unsupported root element: " + rootElement.getClass());
        }
        return result;
    }

    static public Set<Process> computeAllProcesses(DocumentRoot documentRoot) {
        Set<Process> processes = compute(documentRoot);
        LinkedList<Process> unseenProcesses = new LinkedList<>(processes);
        while (!unseenProcesses.isEmpty()) {
            Process current = unseenProcesses.removeFirst();
            for (FlowElement flowElement : current.getFlowElements()) {
                if (flowElement instanceof CallActivity) {
                    CallActivity callActivity = (CallActivity) flowElement;
                    if (callActivity.getCalledElementRef() instanceof Process) {
                        Process calledProcess = (Process) callActivity.getCalledElementRef();
                        if (processes.add(calledProcess)) unseenProcesses.add(calledProcess);
                    }
                }
            }
        }
        return processes;
    }

}
