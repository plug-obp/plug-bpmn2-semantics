package plug.bpmn2.model;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.model.analysis.BPMNInstanceOwnerDiagnosis;
import plug.bpmn2.model.analysis.BPMNeContainmentDiagnosis;
import plug.bpmn2.model.pool.BPMNTokenPool;
import plug.bpmn2.model.printer.BPMNPrinterShort;

public class BPMNModelHandler {

    public final BPMNModelIds idsProvider = new BPMNModelIds();
    public final BPMNeContainmentDiagnosis containment = new BPMNeContainmentDiagnosis();
    public final BPMNInstanceOwnerDiagnosis ownership = new BPMNInstanceOwnerDiagnosis();
    public final BPMNTokenPool tokens = new BPMNTokenPool();
    public final BPMNPrinterShort printer = new BPMNPrinterShort();
    public final DocumentRoot documentRoot;
    
    public BPMNModelHandler(DocumentRoot documentRoot) {
        containment.populate(documentRoot);
        ownership.populate(documentRoot);
        this.documentRoot = documentRoot;
    }

}
