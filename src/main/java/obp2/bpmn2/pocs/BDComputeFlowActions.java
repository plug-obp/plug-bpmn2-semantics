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

import java.util.Set;

public class BDComputeFlowActions {

    static public void main(String[] args) {
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        Set<Process> rootProcesses = RootProcesses.compute(documentRoot);
        TokenPool tokenPool = TokenPoolUtils.compute(rootProcesses);
        SignalData signalData = SignalDataUtils.compute(rootProcesses, SignalIdStrategy::byName);
        FlowActionPool flowActionPool = FlowActionUtils.compute(rootProcesses, tokenPool, signalData);
        System.out.println(FlowActionUtils.getReportString(flowActionPool));
    }

}
