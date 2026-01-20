package obp2.bpmn2.pocs;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;

public class EBExcludeCalledProcessesExploration {

    static public void main(String[] args) {
        System.out.println("Loading ...");

        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        BPMN2ProcessedModel model = new BPMN2ProcessedModel(documentRoot, false);

        System.out.println("Exploring ...");

        EAFlowCompletionExploration.pocFlowCompletionExploration(model, true, 10000);
        EAFlowCompletionExploration.pocFlowCompletionExploration(model, false, 10000);

    }


}
