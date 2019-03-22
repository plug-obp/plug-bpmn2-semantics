package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.interpretation.model.BPMNModelRuntimeState;
import plug.bpmn2.interpretation.tools.base.ParentMap;
import plug.bpmn2.interpretation.tools.base.TokenPool;
import plug.bpmn2.interpretation.tools.base.BPMN2PrinterShort;

import java.util.Arrays;
import java.util.function.Consumer;

public class BPMNRuntimeToolKit {

    private Consumer<String> logOutput;

    private DocumentRoot documentRoot;

    private BPMN2PrinterShort shortPrinter;
    private TokenPool tokenPool;
    private ParentMap parentMap;

    private TokensInitializer tokensInitializer;
    private InstanceFactory instanceFactory;
    private InstanceMap instanceMap;
    private FlowDataAddMissing flowDataInitializer;

    private int logDepth = 0;

    public BPMNRuntimeToolKit() {
        logOutput = s -> {};
    }

    public void setLogOutput(Consumer<String> logOutput) {
        this.logOutput = logOutput;
    }

    public void increaseLogDepth() {
        logDepth += 1;
    }

    public void decreaseLogDepth() {
        logDepth -= 1;
    }

    public void println(String log) {
        char[] tabs = new char[logDepth * 4];
        Arrays.fill(tabs, ' ');
        String tab = new String(tabs);
        logOutput.accept(tab + log);
    }

    private String getString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        if (object instanceof EObject) {
            return shortPrinter.getShortString((EObject) object);
        }
        String className = object.getClass().getTypeName();
        String[] classNames = className.split("\\.");
        return classNames[classNames.length - 1];
    }

    public void println(Object phase, Object subject, String log) {
        String phaseString = getString(phase);
        String subjectString = getString(subject);
        println("[" + phaseString + "] " + log + " <" + subjectString + ">");
    }

    public void setDocumentRoot(DocumentRoot documentRoot) {
        this.documentRoot = documentRoot;

        shortPrinter = new BPMN2PrinterShort();
        tokenPool = new TokenPool();
        parentMap = new ParentMap(documentRoot);

        tokensInitializer = new TokensInitializer(this);
        instanceFactory = new InstanceFactory(this);
        instanceMap = new InstanceMap(this);
        flowDataInitializer = new FlowDataAddMissing(this);
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

}
