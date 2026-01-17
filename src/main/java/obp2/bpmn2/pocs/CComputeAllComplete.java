package obp2.bpmn2.pocs;

import obp2.bpmn2.model.RootProcesses;
import obp2.bpmn2.model.action.FlowActionPool;
import obp2.bpmn2.model.action.FlowActionUtils;
import obp2.bpmn2.model.signal.SignalData;
import obp2.bpmn2.model.signal.SignalDataUtils;
import obp2.bpmn2.model.signal.SignalIdStrategy;
import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.model.token.TokenPoolUtils;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class CComputeAllComplete {

    static private void printDuration(LocalDateTime timeStart) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(timeStart, now);
        System.out.println(duration);
    }

    static public void main(String[] args) {
        LocalDateTime globalTimeStart = LocalDateTime.now();
        LocalDateTime currentTimeStart = globalTimeStart;

        System.out.println("----- Loading -----");

        System.out.print("Parsing ... ");
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        System.out.print("DONE ");
        printDuration(currentTimeStart);

        currentTimeStart = LocalDateTime.now();
        System.out.print("Computing root processes ... ");
        Set<Process> rootProcesses = RootProcesses.compute(documentRoot);
        System.out.print("DONE ");
        printDuration(currentTimeStart);

        currentTimeStart = LocalDateTime.now();
        System.out.print("Pre computing tokens ... ");
        TokenPool tokenPool = TokenPoolUtils.compute(rootProcesses);
        System.out.print("DONE ");
        printDuration(currentTimeStart);

        currentTimeStart = LocalDateTime.now();
        System.out.print("Pre computing signals ... ");
        SignalData signalData = SignalDataUtils.compute(rootProcesses, SignalIdStrategy::byName);
        System.out.print("DONE ");
        printDuration(currentTimeStart);

        currentTimeStart = LocalDateTime.now();
        System.out.print("Pre computing actions ... ");
        FlowActionPool flowActionPool = FlowActionUtils.compute(rootProcesses, tokenPool, signalData);
        System.out.print("DONE ");
        printDuration(currentTimeStart);

        System.out.print("Total duration: ");
        printDuration(globalTimeStart);

        System.out.println("----- Tokens -----");
        System.out.println(TokenPoolUtils.getReportString(tokenPool));

        System.out.println("----- Signals -----");
        System.out.println(SignalDataUtils.getReportString(signalData));

        System.out.println("----- Actions -----");
        System.out.println(FlowActionUtils.getReportString(flowActionPool));
    }

}
