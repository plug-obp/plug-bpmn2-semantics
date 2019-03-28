package plug.bpmn2.interpretation.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.interpretation.tools.analysis.resource.BPMN2PrinterShort;
import plug.bpmn2.interpretation.tools.analysis.resource.ParentMap;

public class ModelTools {

    private final BPMNToolKit toolKit;

    private DocumentRoot documentRoot;

    private BPMN2PrinterShort shortPrinter;

    private ParentMap parentMap;

    public ModelTools(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
    }

}
