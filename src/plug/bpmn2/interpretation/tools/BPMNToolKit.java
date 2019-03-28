package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.tools.analysis.instance.InstanceMap;
import plug.bpmn2.interpretation.tools.analysis.resource.BPMN2PrinterShort;
import plug.bpmn2.interpretation.tools.analysis.resource.ParentMap;
import plug.bpmn2.interpretation.tools.execute.ActionSetSupplier;
import plug.bpmn2.interpretation.tools.instantiate.FlowDataAddMissing;
import plug.bpmn2.interpretation.tools.instantiate.InstanceFactory;
import plug.bpmn2.interpretation.tools.instantiate.TokenPool;
import plug.bpmn2.interpretation.tools.instantiate.TokensInitializer;
import plug.bpmn2.interpretation.transition.ActionSet;

import java.util.Arrays;
import java.util.function.Consumer;

public class BPMNToolKit {

    private BPMNLogger logger;

    private DocumentRoot documentRoot;

    // Model tools
    private BPMN2PrinterShort shortPrinter;
    private TokenPool tokenPool;
    private ParentMap parentMap;

    // System state tools
    private TokensInitializer tokensInitializer;
    private InstanceFactory instanceFactory;
    private InstanceMap instanceMap;
    private FlowDataAddMissing flowDataInitializer;

    // Execution tools
    private ActionSetSupplier actionSetSupplier;

    private int logDepth = 0;

    public BPMNToolKit() {
        logger = new BPMNLogger();
    }

    public BPMNLogger getLogger() {
        return logger;
    }

    public void setLogOutput(Consumer<String> logOutput) {
        logger.setAllOutput(logOutput);
    }

    public void increaseLogDepth() {
        logger.increaseLogDepth();
    }

    public void decreaseLogDepth() {
        logger.decreaseLogDepth();
    }

    public void println(String log) {
        logger.log(log);
    }

    public void println(Object phase, Object subject, String log) {
        logger.log(phase, subject, log);
    }

    public void setDocumentRoot(DocumentRoot documentRoot) {
        this.documentRoot = documentRoot;

        shortPrinter = new BPMN2PrinterShort();
        tokenPool = new TokenPool();
        parentMap = new ParentMap(this);

        tokensInitializer = new TokensInitializer(this);
        instanceFactory = new InstanceFactory(this);
        instanceMap = new InstanceMap(this);
        flowDataInitializer = new FlowDataAddMissing(this);

        actionSetSupplier = new ActionSetSupplier(this);
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

    public InstanceMap getInstanceMap() {
        return instanceMap;
    }

    public FlowDataAddMissing getFlowDataInitializer() {
        return flowDataInitializer;
    }

    public BPMNModelRuntimeState getInitialState() {
        println(this, "Initial State", "Building");
        increaseLogDepth();

        BPMNModelRuntimeState initialState = instanceFactory.instantiate(documentRoot);
        instanceMap.load(initialState);
        flowDataInitializer.initializeFlowData(initialState);

        decreaseLogDepth();
        return initialState;
    }

    public BPMNModelRuntimeState fireTransition(
        BPMNModelRuntimeState source,
        ActionSet action
    ) {
        return null;
    }

}
