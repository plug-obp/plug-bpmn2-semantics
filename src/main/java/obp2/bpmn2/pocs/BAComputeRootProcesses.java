package obp2.bpmn2.pocs;

import obp2.bpmn2.model.RootProcesses;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;

import java.util.Set;

public class BAComputeRootProcesses {

    static public void main(String[] args) {
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        Set<Process> rootProcesses = RootProcesses.compute(documentRoot);
        for (Process process : rootProcesses) {
            System.out.println(process.getName());
        }
    }

}
