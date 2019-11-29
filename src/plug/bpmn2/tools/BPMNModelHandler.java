package plug.bpmn2.tools;

import org.eclipse.bpmn2.DocumentRoot;
import plug.bpmn2.tools.common.BPMNModelId;
import plug.bpmn2.tools.common.dag.BPMNInstanceOwnerDiagnosis;
import plug.bpmn2.tools.common.dag.BPMNeContainmentDiagnosis;
import plug.bpmn2.tools.common.pools.BPMNPools;
import plug.bpmn2.tools.common.pools.BPMNTokenPool;
import plug.bpmn2.tools.common.printer.BPMNPrinterShort;
import plug.bpmn2.tools.runtime.instance.BPMNInstanceFactory;

public class BPMNModelHandler {

    public final BPMNModelId id = new BPMNModelId();
    public final BPMNeContainmentDiagnosis containment = new BPMNeContainmentDiagnosis();
    public final BPMNInstanceOwnerDiagnosis ownership = new BPMNInstanceOwnerDiagnosis();
    public final BPMNInstanceFactory instance = new BPMNInstanceFactory();
    public final BPMNTokenPool tokens = new BPMNTokenPool();
    public final BPMNPrinterShort printer = new BPMNPrinterShort();

    public BPMNModelHandler(DocumentRoot documentRoot) {
        containment.populate(documentRoot);
        ownership.populate(documentRoot);
    }

}
