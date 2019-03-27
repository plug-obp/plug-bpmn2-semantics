package plug.bpmn2.interpretation.tools.analysis.resource;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Error;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:luka.le_roux@ensta-bretagne.fr">Luka Le Roux</a>
 */
public class BPMN2PrinterShort extends Bpmn2Switch<String> {

    public static BPMN2PrinterShort INSTANCE = new BPMN2PrinterShort();

    public String getShortString(EObject object) {
        return doSwitch(object);
    }

    @Override
    public String caseDocumentRoot(DocumentRoot object) {
        return "Document root";
    }

    @Override
    public String caseActivity(Activity object) {
        return "Activity"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseAdHocSubProcess(AdHocSubProcess object) {
        return "AdHoc sub process"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseArtifact(Artifact object) {
        return "Artifact"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseAssignment(Assignment object) {
        return "Assignment"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseAssociation(Association object) {
        return "Association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseAuditing(Auditing object) {
        return "Auditing"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseBaseElement(BaseElement object) {
        return "Base element"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseBoundaryEvent(BoundaryEvent object) {
        return "Boundary event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseBusinessRuleTask(BusinessRuleTask object) {
        return "Business rule task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCallActivity(CallActivity object) {
        return "Call activity"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCallChoreography(CallChoreography object) {
        return "Call choreography"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCallConversation(CallConversation object) {
        return "Call conversation"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCallableElement(CallableElement object) {
        return "Callable element"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCancelEventDefinition(CancelEventDefinition object) {
        return "Cancel event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCatchEvent(CatchEvent object) {
        return "Catch event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCategory(Category object) {
        return "Category"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCategoryValue(CategoryValue object) {
        return "Category value"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseChoreography(Choreography object) {
        return "Choreography"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseChoreographyActivity(ChoreographyActivity object) {
        return "Choreography activity"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseChoreographyTask(ChoreographyTask object) {
        return "Choreography task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCollaboration(Collaboration object) {
        return "Collaboration"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCompensateEventDefinition(CompensateEventDefinition object) {
        return "Compensate event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseComplexBehaviorDefinition(ComplexBehaviorDefinition object) {
        return "Complex behavior definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseComplexGateway(ComplexGateway object) {
        return "Complex gateway"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseConditionalEventDefinition(ConditionalEventDefinition object) {
        return "Conditional event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseConversation(Conversation object) {
        return "Conversation"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseConversationAssociation(ConversationAssociation object) {
        return "Conversation association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseConversationLink(ConversationLink object) {
        return "Conversation link"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseConversationNode(ConversationNode object) {
        return "Conversation node"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCorrelationKey(CorrelationKey object) {
        return "Correlation key"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCorrelationProperty(CorrelationProperty object) {
        return "Correlation property"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCorrelationPropertyBinding(CorrelationPropertyBinding object) {
        return "Correlation property binding"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCorrelationPropertyRetrievalExpression(CorrelationPropertyRetrievalExpression object) {
        return "Correlation property retrieval expression"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseCorrelationSubscription(CorrelationSubscription object) {
        return "Correlation subscription"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataAssociation(DataAssociation object) {
        return "Data association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataInput(DataInput object) {
        return "Data input"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataInputAssociation(DataInputAssociation object) {
        return "Data input association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataObject(DataObject object) {
        return "Data object"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataObjectReference(DataObjectReference object) {
        return "Data object references"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataOutput(DataOutput object) {
        return "Data output"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataOutputAssociation(DataOutputAssociation object) {
        return "Data output association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataState(DataState object) {
        return "Data getActivityState"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataStore(DataStore object) {
        return "Data store"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDataStoreReference(DataStoreReference object) {
        return "Data store reference"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDefinitions(Definitions object) {
        return "Definitions"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseDocumentation(Documentation object) {
        return "Documentation"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEndEvent(EndEvent object) {
        return "End event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEndPoint(EndPoint object) {
        return "End point"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseError(Error object) {
        return "Error"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseErrorEventDefinition(ErrorEventDefinition object) {
        return "Error event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEscalation(Escalation object) {
        return "Escalation"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEscalationEventDefinition(EscalationEventDefinition object) {
        return "Escalation event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEvent(Event object) {
        return "Event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEventBasedGateway(EventBasedGateway object) {
        return "Event based gateway"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseEventDefinition(EventDefinition object) {
        return "Event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseExclusiveGateway(ExclusiveGateway object) {
        return "Exclusive gateway"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseExpression(Expression object) {
        return "Expression"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseExtension(Extension object) {
        return "Extension";
    }

    @Override
    public String caseExtensionAttributeDefinition(ExtensionAttributeDefinition object) {
        return "Extension attribute definition"
                + " " + attributeString("name", object.getName());
    }

    @Override
    public String caseExtensionAttributeValue(ExtensionAttributeValue object) {
        return "Extension attribute value";
    }

    @Override
    public String caseExtensionDefinition(ExtensionDefinition object) {
        return "Extension definition"
                + " " + attributeString("name", object.getName());
    }

    @Override
    public String caseFlowElement(FlowElement object) {
        return "Flow element"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseFlowElementsContainer(FlowElementsContainer object) {
        return "Flow element container"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseFlowNode(FlowNode object) {
        return "Flow node"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseFormalExpression(FormalExpression object) {
        return "Formal expression"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGateway(Gateway object) {
        return "Gateway"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalBusinessRuleTask(GlobalBusinessRuleTask object) {
        return "Global business rule task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalChoreographyTask(GlobalChoreographyTask object) {
        return "Global choreography task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalConversation(GlobalConversation object) {
        return "Global conversation"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalManualTask(GlobalManualTask object) {
        return "Global manual task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalScriptTask(GlobalScriptTask object) {
        return "Global script task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalTask(GlobalTask object) {
        return "Global task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGlobalUserTask(GlobalUserTask object) {
        return "Global user task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseGroup(Group object) {
        return "Group"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseHumanPerformer(HumanPerformer object) {
        return "Human performer"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseImplicitThrowEvent(ImplicitThrowEvent object) {
        return "Implicit throw event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseImport(Import object) {
        return "Import";
    }

    @Override
    public String caseInclusiveGateway(InclusiveGateway object) {
        return "Inclusive gateway"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseInputOutputBinding(InputOutputBinding object) {
        return "Output binding"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseInputOutputSpecification(InputOutputSpecification object) {
        return "Input output specification"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseInputSet(InputSet object) {
        return "Input set"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseInteractionNode(InteractionNode object) {
        return "Interaction node";
    }

    @Override
    public String caseInterface(Interface object) {
        return "Interface"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseIntermediateCatchEvent(IntermediateCatchEvent object) {
        return "Intermediate catch event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseIntermediateThrowEvent(IntermediateThrowEvent object) {
        return "Intermediate throw event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseItemAwareElement(ItemAwareElement object) {
        return "Item aware element"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseItemDefinition(ItemDefinition object) {
        return "Item definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseLane(Lane object) {
        return "Lane"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseLaneSet(LaneSet object) {
        return "Lane set"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseLinkEventDefinition(LinkEventDefinition object) {
        return "Link event definition"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseLoopCharacteristics(LoopCharacteristics object) {
        return "Loop characteristics"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseManualTask(ManualTask object) {
        return "Manual task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseMessage(Message object) {
        return "Message"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseMessageEventDefinition(MessageEventDefinition object) {
        return "Message event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseMessageFlow(MessageFlow object) {
        return "Message flow"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseMessageFlowAssociation(MessageFlowAssociation object) {
        return "Message flow association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseMonitoring(Monitoring object) {
        return "Monitoring"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseMultiInstanceLoopCharacteristics(MultiInstanceLoopCharacteristics object) {
        return "Multi instance loop characteristics"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseOperation(Operation object) {
        return "Operation"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseOutputSet(OutputSet object) {
        return "Output set"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseParallelGateway(ParallelGateway object) {
        return "Parallel gateway"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseParticipant(Participant object) {
        return "Participant"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseParticipantAssociation(ParticipantAssociation object) {
        return "Participant association"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseParticipantMultiplicity(ParticipantMultiplicity object) {
        return "Participant multiplicity"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String casePartnerEntity(PartnerEntity object) {
        return "Partner entity"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String casePartnerRole(PartnerRole object) {
        return "Partner role"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String casePerformer(Performer object) {
        return "Performer"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String casePotentialOwner(PotentialOwner object) {
        return "Potential owner"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseProcess(Process object) {
        return "Process"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseProperty(Property object) {
        return "Property"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseReceiveTask(ReceiveTask object) {
        return "Receive task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseRelationship(Relationship object) {
        return "Relationship"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseRendering(Rendering object) {
        return "Rendering"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseResource(Resource object) {
        return "Resoucrce"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseResourceAssignmentExpression(ResourceAssignmentExpression object) {
        return "Resource assignment expression"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseResourceParameter(ResourceParameter object) {
        return "Resource parameter"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseResourceParameterBinding(ResourceParameterBinding object) {
        return "Resource parameter binding"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseResourceRole(ResourceRole object) {
        return "Resource role"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseRootElement(RootElement object) {
        return "Root element"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseScriptTask(ScriptTask object) {
        return "Script task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSendTask(SendTask object) {
        return "Send task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSequenceFlow(SequenceFlow object) {
        return "Sequence flow"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseServiceTask(ServiceTask object) {
        return "Service task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSignal(Signal object) {
        return "Signal"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSignalEventDefinition(SignalEventDefinition object) {
        return "Signal event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseStandardLoopCharacteristics(StandardLoopCharacteristics object) {
        return "Standard loop characteristics"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseStartEvent(StartEvent object) {
        return "Start event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSubChoreography(SubChoreography object) {
        return "Sub choreography"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSubConversation(SubConversation object) {
        return "Sub conversation"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseSubProcess(SubProcess object) {
        return "Sub process"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseTask(Task object) {
        return "Task"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseTerminateEventDefinition(TerminateEventDefinition object) {
        return "Terminate event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseTextAnnotation(TextAnnotation object) {
        return "Text annotation"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseThrowEvent(ThrowEvent object) {
        return "Throw event"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseTimerEventDefinition(TimerEventDefinition object) {
        return "Time event definition"
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseTransaction(Transaction object) {
        return "Transaction"
                + " " + attributeString("name", object.getName())
                + " " + attributeString("id", object.getId());
    }

    @Override
    public String caseUserTask(UserTask object) {
        return "User task"
                + " " + attributeString("name", object.getName());
    }

    @Override
    public String defaultCase(EObject object) {
        return "Unrecognized EObject [" + object.toString() + "]";
    }

    private String attributeString(String attributeName, String value) {
        return "<" + attributeName + "=" + value + ">";
    }

}
