package obp2.bpmn2.pocs;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.RootProcesses;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;

public class FBAllExplorationsPerProcess {

    static public void exploreOneProcessAsRootProcess(DocumentRoot documentRoot, Process process, int cap) {
        System.out.println("Exploring with \"" + process.getName() + "\" as the root process ...");
        BPMN2ProcessedModel processedModel = new BPMN2ProcessedModel(documentRoot, process);
        System.out.println("Regular Exploration");
        DANaiveExploration.pocNaiveExploration(processedModel, true, cap);
        DANaiveExploration.pocNaiveExploration(processedModel, false, cap);
        System.out.println("Flow completion Exploration");
        EAFlowCompletionExploration.pocFlowCompletionExploration(processedModel, true, cap);
        EAFlowCompletionExploration.pocFlowCompletionExploration(processedModel, false, cap);
        processedModel = new BPMN2ProcessedModel(documentRoot, process, false);
        System.out.println("Flow completion Exploration & handling all CallActivity as Task");
        EAFlowCompletionExploration.pocFlowCompletionExploration(processedModel, true, cap);
        EAFlowCompletionExploration.pocFlowCompletionExploration(processedModel, false, cap);
        System.out.println("Amnesic, flow completion Exploration & handling all CallActivity as Task");
        FAAmnesicExploration.pocExploration(processedModel,
                true, cap, EAFlowCompletionExploration::flowCompletionNext);
        FAAmnesicExploration.pocExploration(processedModel,
                false, cap, EAFlowCompletionExploration::flowCompletionNext);
        System.out.println();

    }

    static public void exploreAllProcessesAsRootProcess(DocumentRoot documentRoot, int cap) {
        for (Process process : RootProcesses.computeAllProcesses(documentRoot)) {
            exploreOneProcessAsRootProcess(documentRoot, process, cap);
        }
    }

    static public void main(String[] args) {
        System.out.println("Loading ...");
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        exploreAllProcessesAsRootProcess(documentRoot, 100000);
    }

}
