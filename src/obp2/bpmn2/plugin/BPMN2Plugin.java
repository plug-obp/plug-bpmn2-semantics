package obp2.bpmn2.plugin;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.plugin.json.BPSLIFile;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import obp2.runtime.core.ILanguageModule;
import obp2.runtime.core.ILanguagePlugin;
import org.eclipse.bpmn2.DocumentRoot;

import java.net.URI;
import java.nio.file.Paths;
import java.util.function.Function;

public class BPMN2Plugin implements ILanguagePlugin<URI, BPMN2ExecutionState, BPMN2FlowAction, Void> {

    @Override
    public String getName() {
        return "BPMN2";
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".bpmn", ".bpmn2", BPSLIFile.EXTENSION};
    }

    @Override
    public Function<URI, ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void>> languageModuleFunction() {
        return this::getLanguageModule;
    }

    public BPMN2Module getLanguageModule(URI uri) {
        BPSLIFile settings;
        URI fileURI;
        if (uri.toString().toLowerCase().contains(BPSLIFile.EXTENSION)) {
            settings = BPSLIFile.loadFromFile(uri);
            String parentFileName = Paths.get(uri).getFileName().toString();
            String modelFilePath = uri.getPath().replace(
                    parentFileName,
                    settings.modelFileName.replace(" ", "%20")
            );
            fileURI = URI.create(modelFilePath);
        } else {
            settings = new BPSLIFile();
            fileURI = uri;
        }
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(fileURI);
        BPMN2ProcessedModel model = new BPMN2ProcessedModel(
                documentRoot,
                settings.includeCalledProcesses
        );
        return new BPMN2Module(model, settings.flowCompletion);
    }

}
