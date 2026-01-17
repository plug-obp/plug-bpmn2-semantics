package obp2.bpmn2.pocs;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;

public class DBNaiveExplorationParaGateways {

    static public void main(String[] args) {
        System.out.println("Loading ...");

        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.PARALLEL_GATEWAYS_FILE_PATH);
        BPMN2ProcessedModel model = new BPMN2ProcessedModel(documentRoot);

        System.out.println("Exploring ...");

        DANaiveExploration.pocNaiveExploration(model, true, 10000);
        DANaiveExploration.pocNaiveExploration(model, false, 10000);
    }

}
