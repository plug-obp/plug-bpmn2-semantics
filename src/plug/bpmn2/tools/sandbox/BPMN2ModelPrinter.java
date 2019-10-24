package plug.bpmn2.tools.sandbox;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

import java.util.LinkedList;
import java.util.List;

public class BPMN2ModelPrinter {

    static private final String TAB = "    ";
    private final BPMN2PrinterShort printerShort = new BPMN2PrinterShort();
    private final InternalSwitch internalSwitch = new InternalSwitch();

    public String getString(DocumentRoot root) {
        String result = "";
        for (String line : internalSwitch.doSwitch(root)) {
            result += line + "\n";
        }
        return result;
    }

    class InternalSwitch extends Bpmn2Switch<List<String>> {

        private void addWithTab(List<String> target, List<String> source) {
            for (String string : source) {
                target.add(TAB + string);
            }
        }

        private void addRef(List<String> target, String fieldName, EObject ref) {
            String refString = ref == null ? "null" : printerShort.getShortString(ref);
            target.add(TAB + fieldName + " Ref: " + refString);
        }

        @Override
        public List<String> caseDocumentRoot(DocumentRoot object) {
            List<String> result = new LinkedList();
            for (RootElement rootElement : object.getDefinitions().getRootElements()) {
                result.addAll(doSwitch(rootElement));
            }
            return result;
        }

        @Override
        public List<String> caseDefinitions(Definitions object) {
            List<String> result = new LinkedList<>();
            for (RootElement rootElement : object.getRootElements()) {
                result.addAll(doSwitch(rootElement));
            }
            return result;
        }

        private void addFlowElements(List<String> target, FlowElementsContainer object) {
            for (FlowElement flowElement : object.getFlowElements()) {
                addWithTab(target, doSwitch(flowElement));
            }
        }

        @Override
        public List<String> caseProcess(Process object) {
            List<String> result = defaultCase(object);
            addFlowElements(result, object);
            return result;
        }

        @Override
        public List<String> caseCollaboration(Collaboration object) {
            List<String> result = defaultCase(object);
            for (MessageFlow messageFlow : object.getMessageFlows()) {
                addWithTab(result, doSwitch(messageFlow));
            }
            for (Participant participant : object.getParticipants()) {
                addWithTab(result, doSwitch(participant));
            }
            return result;
        }

        @Override
        public List<String> caseMessageFlow(MessageFlow object) {
            List<String> result = defaultCase(object);
            addRef(result, "Source", object.getSourceRef());
            addRef(result, "Target", object.getTargetRef());
            addRef(result, "Message", object.getMessageRef());
            return result;
        }

        @Override
        public List<String> caseParticipant(Participant object) {
            List<String> result = defaultCase(object);
            ParticipantMultiplicity multiplicity = object.getParticipantMultiplicity();
            String multiplicityString = multiplicity == null ?
                    "null" :
                    "[" + multiplicity.getMaximum() + " - " + multiplicity.getMaximum() + "]";
            result.add(TAB + "Multiplicity: " + multiplicityString);
            addRef(result, "Process", object.getProcessRef());
            return result;
        }

        @Override
        public List<String> caseSequenceFlow(SequenceFlow object) {
            List<String> result = defaultCase(object);
            addRef(result, "Source", object.getSourceRef());
            addRef(result, "Target", object.getTargetRef());
            return result;
        }

        @Override
        public List<String> caseFlowNode(FlowNode object) {
            List<String> result = defaultCase(object);
            for (Lane lane : object.getLanes()) {
                addRef(result, "Lane", lane);
            }
            for (SequenceFlow incoming : object.getIncoming()) {
                addRef(result, "Incoming Sequence Flow", incoming);
            }
            for (SequenceFlow outgoing : object.getOutgoing()) {
                addRef(result, "Outgoing Sequence Flow", outgoing);
            }
            return result;
        }

        private void addInputs(List<String> target, List<DataInputAssociation> inputs) {
            for (DataInputAssociation input : inputs) {
                addRef(target, "Data Input Association", input);
            }
        }

        private void addOutputs(List<String> target, List<DataOutputAssociation> outputs) {
            for (DataOutputAssociation output : outputs) {
                addRef(target, "Data Output Association", output);
            }
        }

        @Override
        public List<String> caseActivity(Activity object) {
            List<String> result = caseFlowNode(object);
            for (BoundaryEvent boundaryEvent : object.getBoundaryEventRefs()) {
                addRef(result, "Boundary Event", boundaryEvent);
            }
            addInputs(result, object.getDataInputAssociations());
            addOutputs(result, object.getDataOutputAssociations());
            return result;
        }

        @Override
        public List<String> caseEvent(Event object) {
            List<String> result = caseFlowNode(object);
            for (ConversationLink conversationLink : object.getIncomingConversationLinks()) {
                addRef(result, "Incoming Conversation Link", conversationLink);
            }
            for (ConversationLink conversationLink : object.getOutgoingConversationLinks()) {
                addRef(result, "Outgoing Conversation Link", conversationLink);
            }
            return result;
        }

        @Override
        public List<String> caseThrowEvent(ThrowEvent object) {
            List<String> result = caseEvent(object);
            addInputs(result, object.getDataInputAssociation());
            // TODO
            return result;
        }

        @Override
        public List<String> caseCatchEvent(CatchEvent object) {
            List<String> result = caseEvent(object);
            return super.caseCatchEvent(object);
        }

        @Override
        public List<String> caseSubProcess(SubProcess object) {
            List<String> result = caseActivity(object);
            addFlowElements(result, object);
            return result;
        }

        @Override
        public List<String> defaultCase(EObject object) {
            List<String> result = new LinkedList<>();
            result.add(printerShort.getShortString(object));
            return result;
        }

    }

}
