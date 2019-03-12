package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;

import java.util.function.Consumer;

public class BPMNRuntimeToolKit {

    private Consumer<String> logOutput;
    private DocumentRoot model;
    private TokenPool tokenPool;
    private TokensInitializer tokensInitializer;
    private InstanceFactory instanceFactory;
    private InstanceMap instanceMap;
    private FlowDataAddMissing flowDataInitializer;

    public BPMNRuntimeToolKit(DocumentRoot model) {
        logOutput = s -> {};
    }

    public void setLogOutput(Consumer<String> logOutput) {
        this.logOutput = logOutput;
    }

    public void println(String log) {
        logOutput.accept(log);
    }

    public void println(String phase, String subject, String log) {
        println("[" + phase + "] <" + subject + "> " + log);
    }

    public void setModel(DocumentRoot model) {
        this.model = model;
        tokenPool = new TokenPool();
        tokensInitializer = new TokensInitializer(this);
        instanceFactory = new InstanceFactory(this);
        instanceMap = new InstanceMap(this);
        flowDataInitializer = new FlowDataAddMissing(this);
    }

    public DocumentRoot getModel() {
        return model;
    }

    public TokenPool getTokenPool() {
        return tokenPool;
    }

    public TokensInitializer getTokensInitializer() {
        return tokensInitializer;
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
        println(this.getClass().toString(), "Initial State", "Building");
        BPMNRuntimeInstance root = instanceFactory.instantiate(model);
        BPMNModelRuntimeState initialState = new BPMNModelRuntimeState(root);
        instanceMap.load(initialState);
        flowDataInitializer.initializeFlowData(initialState);
        return initialState;
    }

}
