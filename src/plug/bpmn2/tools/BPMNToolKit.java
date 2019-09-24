package plug.bpmn2.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.transition.AbstractTransition;
import plug.bpmn2.tools.common.BPMNLogger;
import plug.bpmn2.tools.instance.FlowDataAddMissing;
import plug.bpmn2.tools.instance.InstanceFactory;
import plug.bpmn2.tools.instance.TokenPool;
import plug.bpmn2.tools.instance.TokensInitializer;
import plug.bpmn2.tools.interpretation.AbstractTransitionSupplier;
import plug.bpmn2.tools.model.ParentMap;

import java.util.function.Consumer;
import java.util.logging.Level;

public class BPMNToolKit {

    private BPMNLogger logger;

    private DocumentRoot documentRoot;

    // Model tools
    private TokenPool tokenPool;
    private ParentMap parentMap;

    // System state tools
    private TokensInitializer tokensInitializer;
    private InstanceFactory instanceFactory;
    private TokenPool.InstanceMap instanceMap;
    private FlowDataAddMissing flowDataInitializer;

    // Execution tools
    private AbstractTransitionSupplier abstractTransitionSupplier;

    public BPMNToolKit() {
        this.logger = new BPMNLogger();
    }

    public BPMNLogger getLogger() {
        return logger;
    }

    public void setLogOutput(Consumer<String> logOutput) {
        logger.setOutput(logOutput);
    }

    public void log(String log) {
        logger.log(log);
    }

    public void log(Object phase, Object subject, String log) {
        logger.log(phase, subject, log);
    }

    public void warning(Object phase, Object subject, String log) {
        logger.log(Level.WARNING, phase, subject, log);
    }

    public void setDocumentRoot(DocumentRoot documentRoot) {
        this.documentRoot = documentRoot;

        tokenPool = new TokenPool();
        parentMap = new ParentMap(this);

        tokensInitializer = new TokensInitializer(this);
        instanceFactory = new InstanceFactory(this);
        instanceMap = new TokenPool.InstanceMap(this);
        flowDataInitializer = new FlowDataAddMissing(this);

        abstractTransitionSupplier = new AbstractTransitionSupplier(this);
    }

    public DocumentRoot getDocumentRoot() {
        return documentRoot;
    }

    public TokenPool getTokenPool() {
        return tokenPool;
    }

    public TokensInitializer getTokensInitializer() {
        return tokensInitializer;
    }

    public ParentMap getParentMap() {
        return parentMap;
    }

    public InstanceFactory getInstanceFactory() {
        return instanceFactory;
    }

    public TokenPool.InstanceMap getInstanceMap() {
        return instanceMap;
    }

    public FlowDataAddMissing getFlowDataInitializer() {
        return flowDataInitializer;
    }

    public BPMNModelRuntimeState getInitialState() {
        log(this, "Initial State", "Building");

        BPMNModelRuntimeState initialState = instanceFactory.instantiate(documentRoot);
        instanceMap.load(initialState);
        flowDataInitializer.initializeFlowData(initialState);

        return initialState;
    }

    public BPMNModelRuntimeState fireTransition(
        BPMNModelRuntimeState source,
        AbstractTransition action
    ) {
        return null;
    }

}
