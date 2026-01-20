package obp2.bpmn2.pocs;

import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;

public class ALoadingFiles {

    static public final String ONE_WAY_FILE_PATH = "resources/tests/oneWay_reloaded.bpmn";
    static public final String ONE_WAY_REWORKED_FILE_PATH = "resources/tests/oneWay.bpmn";
    static public final String PARALLEL_GATEWAYS_FILE_PATH = "resources/tests/parallel_gateway.bpmn";

    static public void main(String[] args) {
        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ONE_WAY_FILE_PATH);
        System.out.println(documentRoot);
    }

}
