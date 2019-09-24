package plug.bpmn2.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.tools.common.BPMN2PrinterShort;
import plug.bpmn2.tools.model.ParentMap;

public class ModelTools {

    private final BPMNToolKit toolKit;
    private final BPMN2PrinterShort shortPrinter;
    private final ParentMap parentMap;

    private DocumentRoot documentRoot;

    public ModelTools(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
        shortPrinter = new BPMN2PrinterShort();
        parentMap = new ParentMap(toolKit);
    }



}
