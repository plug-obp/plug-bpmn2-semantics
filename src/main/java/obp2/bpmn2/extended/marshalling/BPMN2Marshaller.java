package obp2.bpmn2.extended.marshalling;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.plugin.BPMN2Module;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.runtime.core.ILanguageModule;
import obp2.runtime.core.IMarshaller;
import plug.utils.marshaling.Marshaller;
import plug.utils.marshaling.Unmarshaller;

import java.io.*;

public class BPMN2Marshaller implements IMarshaller<BPMN2ExecutionState, BPMN2FlowAction, Void> {

    private BPMN2Module module;

    @Override
    public ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> getModule() {
        return module;
    }

    @Override
    public void setModule(ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> module) {
        if (!(module instanceof BPMN2Module)) {
            throw new RuntimeException("BPMN2Module expected");
        }
        this.module = (BPMN2Module) module;
    }

    @Override
    public byte[] serializeConfiguration(BPMN2ExecutionState configuration) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            writeState(configuration, os);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception while writing a state", e);
        }
        return os.toByteArray();
    }

    @Override
    public byte[] serializeFireable(BPMN2FlowAction fireable) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            writeAction(fireable, os);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception while writing an action", e);
        }
        return os.toByteArray();
    }

    @Override
    public byte[] serializeOutput(Void output) {
        return new byte[0];
    }

    @Override
    public BPMN2ExecutionState deserializeConfiguration(byte[] buffer) {
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        try {
            return readState(is);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception while reading a state", e);
        }
    }

    @Override
    public BPMN2FlowAction deserializeFireable(byte[] buffer) {
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        try {
            return readAction(module.getModel(), is);
        } catch (IOException e) {
            throw new RuntimeException("IO Exception while reading an action", e);
        }
    }

    @Override
    public Void deserializeOutput(byte[] buffer) {
        return null;
    }

    static private void writeState(BPMN2ExecutionState state, OutputStream os) throws IOException {
        Marshaller.writeIntArray(state.tokens, os);
    }

    static private BPMN2ExecutionState readState(InputStream is) throws IOException {
        int[] tokens = new int[Unmarshaller.readInt(is)];
        for (int index = 0; index < tokens.length; index++) {
            tokens[index] = Unmarshaller.readInt(is);
        }
        return new BPMN2ExecutionState(tokens);
    }

    static private void writeAction(BPMN2FlowAction action, OutputStream os) throws IOException {
        Marshaller.writeInt(action.getId(), os);
    }

    static private BPMN2FlowAction readAction(BPMN2ProcessedModel model, InputStream is) throws IOException {
        int actionId = Unmarshaller.readInt(is);
        return model.getFlowActionPool().getFlowAction(actionId);
    }

}
