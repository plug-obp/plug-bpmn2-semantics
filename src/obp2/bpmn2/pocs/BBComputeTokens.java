package obp2.bpmn2.pocs;

import obp2.bpmn2.model.RootProcesses;
import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.model.token.TokenPoolUtils;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Process;

import java.util.Set;

public class BBComputeTokens {

    static public void main(String[] args) {
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        Set<Process> rootProcesses = RootProcesses.compute(documentRoot);
        TokenPool tokenPool = TokenPoolUtils.compute(rootProcesses);
        System.out.println(TokenPoolUtils.getReportString(tokenPool));
    }

}
