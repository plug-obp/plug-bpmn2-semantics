package plug.bpmn2.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.tools.common.BPMNPrinterShort;
import plug.bpmn2.tools.model.ParentMap;

public class ModelTools {

    private final BPMNToolKit toolKit;
    private final BPMNPrinterShort shortPrinter;
    private final ParentMap parentMap;

    private DocumentRoot documentRoot;

    public ModelTools(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
        shortPrinter = new BPMNPrinterShort();
        parentMap = new ParentMap(toolKit);
    }



}
