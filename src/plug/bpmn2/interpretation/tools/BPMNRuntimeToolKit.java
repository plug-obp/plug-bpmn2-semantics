package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.interpretation.model.instance.BPMNRuntimeInstance;

public class BPMNRuntimeToolKit {

    private final DocumentRoot model;

    private final TokenPool tokenPool;

    private final TokensInitializer tokensInitializer;

    private final InstanceFactory instanceFactory;

    public BPMNRuntimeToolKit(DocumentRoot model) {
        this.model = model;

        tokenPool = new TokenPool();

        tokensInitializer = new TokensInitializer(tokenPool);

        instanceFactory = new InstanceFactory(tokensInitializer);
    }

    public DocumentRoot getModel() {
        return model;
    }

    public BPMNRuntimeInstance getInitialState() {
        return getInstanceFactory().instantiate(model);
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

}
