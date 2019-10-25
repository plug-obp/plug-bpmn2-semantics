package plug.bpmn2.tools.sandbox.preprocessing.data;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.model.instance.data.Token;
import java.util.HashMap;
import java.util.Map;

public class BPMNTokenSupplier {

    private Map<SequenceFlow, Token> sequenceFlowTokenMap = new HashMap<>();

    public void populate(DocumentRoot documentRoot) {
        sequenceFlowTokenMap.clear();
        new InternalSwitch().doSwitch(documentRoot);
    }

    public Token get(SequenceFlow sequenceFlow) {
        return sequenceFlowTokenMap.get(sequenceFlow);
    }

    class InternalSwitch extends Bpmn2Switch<Object> {

        private final Object NON_NULL_TAG = 0;

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            for (RootElement rootElement : object.getDefinitions().getRootElements()) {
                doSwitch(rootElement);
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseFlowElementsContainer(FlowElementsContainer object) {
            for (FlowElement flowElement : object.getFlowElements()) {
                doSwitch(flowElement);
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseSequenceFlow(SequenceFlow object) {
            if (sequenceFlowTokenMap.get(object) == null) {
                sequenceFlowTokenMap.put(object, new Token(object));
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseFlowNode(FlowNode object) {
            return NON_NULL_TAG;
        }

        @Override
        public Object caseCollaboration(Collaboration object) {
            for (Participant participant : object.getParticipants()) {
                Process process = participant.getProcessRef();
                if (process != null) {
                    doSwitch(process);
                }
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseChoreography(Choreography object) {
            caseFlowElementsContainer(object);
            caseCollaboration(object);
            return NON_NULL_TAG;
        }
    }

}
