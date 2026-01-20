package obp2.bpmn2.semantics.runtime;

import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.core.IConfiguration;

import java.util.Arrays;

/**
 * Runtime adapter that extends the base BPMN2ExecutionState and implements IConfiguration
 * for OBP2 runtime integration.
 */
public class BPMN2RuntimeConfiguration extends BPMN2ExecutionState 
        implements IConfiguration<BPMN2RuntimeConfiguration> {

    private Object metadata;

    public BPMN2RuntimeConfiguration(int[] tokens) {
        super(tokens);
    }

    @Override
    public Object getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    @Override
    public BPMN2RuntimeConfiguration createCopy() {
        int[] copyTokens = Arrays.copyOf(tokens, tokens.length);
        BPMN2RuntimeConfiguration copy = new BPMN2RuntimeConfiguration(copyTokens);
        copy.setMetadata(this.metadata);
        return copy;
    }
}
