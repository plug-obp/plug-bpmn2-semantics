package obp2.bpmn2.model;

import obp2.bpmn2.model.action.FlowActionPool;
import obp2.bpmn2.model.action.FlowActionUtils;
import obp2.bpmn2.model.observers.Observers;
import obp2.bpmn2.model.signal.SignalData;
import obp2.bpmn2.model.signal.SignalDataUtils;
import obp2.bpmn2.model.signal.SignalIdStrategy;
import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.model.token.TokenPoolUtils;
import obp2.bpmn2.plugin.json.BPSLIFile;
import obp2.bpmn2.utils.BPMN2ModelValidation;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class BPMN2ProcessedModel {

    private final DocumentRoot documentRoot;
    private final Set<Process> rootProcesses;
    private final TokenPool tokenPool;
    private final SignalData signalData;
    private final FlowActionPool flowActionPool;
    private final ProcessInstances processInstances;
    private final Observers observers;

    public BPMN2ProcessedModel(DocumentRoot documentRoot, Set<Process> rootProcesses, boolean includeCalledProcesses) {
        if (BPMN2ModelValidation.nodesAreMissingFlows(rootProcesses)) {
            BPMN2ModelValidation.addMissingFlows(rootProcesses);
        }
        this.documentRoot = documentRoot;
        this.rootProcesses = rootProcesses;
        tokenPool = TokenPoolUtils.compute(rootProcesses, includeCalledProcesses);
        signalData = SignalDataUtils.compute(rootProcesses, SignalIdStrategy::byName);
        flowActionPool = FlowActionUtils.compute(rootProcesses, tokenPool, signalData);
        processInstances = new ProcessInstances(tokenPool);
        observers = new Observers();
    }

    public BPMN2ProcessedModel(DocumentRoot documentRoot, Set<Process> rootProcesses) {
        this(documentRoot, rootProcesses, true);
    }

    public BPMN2ProcessedModel(DocumentRoot documentRoot, Process process) {
        this(documentRoot, Collections.singleton(process));
    }

    public BPMN2ProcessedModel(DocumentRoot documentRoot, Process process, boolean includeCalledProcesses) {
        this(documentRoot, Collections.singleton(process), includeCalledProcesses);
    }

    public BPMN2ProcessedModel(DocumentRoot documentRoot, boolean includeCalledProcesses) {
        this(documentRoot, RootProcesses.compute(documentRoot), includeCalledProcesses);
    }

    public BPMN2ProcessedModel(DocumentRoot documentRoot) {
        this(documentRoot, true);
    }

    public DocumentRoot getDocumentRoot() {
        return documentRoot;
    }

    public Set<Process> getRootProcesses() {
        return rootProcesses;
    }

    public TokenPool getTokenPool() {
        return tokenPool;
    }

    public SignalData getSignalData() {
        return signalData;
    }

    public FlowActionPool getFlowActionPool() {
        return flowActionPool;
    }

    public Process getProcess(List<CallActivity> callStack) {
        return processInstances.getProcess(callStack);
    }

    public Observers getObservers() {
        return observers;
    }

}
