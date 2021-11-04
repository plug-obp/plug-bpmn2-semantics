package plug.bpmn2.model.analysis;

import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.LinkedList;

public class BPMNInstanceOwnerDiagnosis extends AbstractDAGDiagnosis<EObject> {

    @Override
    void fillNodeTargetSourceSets(EObject rootObject) {
        new InternalSwitch().doSwitch(rootObject);
    }

    class InternalSwitch extends Bpmn2Switch<Object> {

        private final Object NON_NULL_TAG = 0;

        private LinkedList<EObject> ownerStack = new LinkedList<>();

        private boolean openInstance(EObject object) {
            if (!ownerStack.isEmpty()) {
                getTargetSet(ownerStack.getLast()).add(object);
                getSourceSet(object).add(ownerStack.getLast());
            }
            if (getNodeSet().add(object)) {
                ownerStack.addLast(object);
                return true;
            }
            return false;
        }

        private void closeInstance() {
            ownerStack.removeLast();
        }

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            for (RootElement rootElement : object.getDefinitions().getRootElements()) {
                doSwitch(rootElement);
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseCollaboration(Collaboration object) {
            if (openInstance(object)) {
                for (Participant participant : object.getParticipants()) {
                    doSwitch(participant.getProcessRef());
                }
                closeInstance();
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseProcess(Process object) {
            if (openInstance(object)) {
                for (FlowElement flowElement : object.getFlowElements()) {
                    doSwitch(flowElement);
                }
                closeInstance();
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseActivity(Activity object) {
            if (openInstance(object)) {
                closeInstance();
            }
            return NON_NULL_TAG;
        }

        @Override
        public Object caseGateway(Gateway object) {
            // Can not be instantiated
            return NON_NULL_TAG;
        }

        @Override
        public Object caseEvent(Event object) {
            // Can not be instantiated
            return NON_NULL_TAG;
        }

        @Override
        public Object caseSequenceFlow(SequenceFlow object) {
            // Can not be instantiated
            return NON_NULL_TAG;
        }

        @Override
        public Object caseMessage(Message object) {
            // Can not be instantiated
            return NON_NULL_TAG;
        }

        @Override
        public Object defaultCase(EObject object) {
            if (openInstance(object)) {
                closeInstance();
            }
            return NON_NULL_TAG;
        }

    }
}
