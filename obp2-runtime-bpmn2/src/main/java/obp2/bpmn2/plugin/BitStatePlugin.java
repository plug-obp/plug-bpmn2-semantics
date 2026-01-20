package obp2.bpmn2.plugin;

import obp2.operators.bytearray.ByteArrayLanguageModule;
import obp2.runtime.core.ILanguageModule;
import obp2.runtime.core.ILanguagePlugin;

import java.net.URI;
import java.util.function.Function;

public class BitStatePlugin  implements ILanguagePlugin<URI, byte[], byte[], byte[]> {

    private final BPMN2Plugin bpmn2Plugin = new BPMN2Plugin();

    @Override
    public String getName() {
        return bpmn2Plugin.getName() + " bitState";
    }

    @Override
    public String[] getExtensions() {
        return bpmn2Plugin.getExtensions();
    }

    @Override
    public Function<URI, ILanguageModule<byte[], byte[], byte[]>> languageModuleFunction() {
        return this::getModule;
    }

    public ByteArrayLanguageModule getModule(URI uri) {
        ILanguageModule<?, ?, ?> bpmn2module = bpmn2Plugin.getLanguageModule(uri);
        return new ByteArrayLanguageModule(
                (ILanguageModule<Object, Object, Object>) bpmn2module
        );
    }
}
