package plug.bpmn2.semantics2;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.semantics2.domains.instances.CollaborationInstance;
import plug.bpmn2.semantics2.domains.instances.ModelInstance;
import plug.bpmn2.semantics2.domains.instances.ProcessInstance;
import plug.bpmn2.semantics2.domains.instances.RootElementInstance;
import plug.bpmn2.semantics2.domains.values.DataStoreValue;
import plug.bpmn2.semantics2.domains.values.ProcessToken;
import plug.bpmn2.semantics2.domains.values.Token;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class BPMN2InitialConfigurationProvider {

    public ModelInstance getTopLevelInstance(DocumentRoot root) {
        Switch bpmn2InitialConfigurationCollector = new Switch();
        bpmn2InitialConfigurationCollector.doSwitch(root);

        return (ModelInstance)bpmn2InitialConfigurationCollector.get(root);
    }

    class Switch extends Bpmn2Switch<Object> {
        Map<EObject, Object> elementMap = new IdentityHashMap<>();

        Map<Process, ProcessInstance> processInstanceMap = new IdentityHashMap<>();
        Map<RootElementInstance, Collaboration> processInCollaborationMap = new IdentityHashMap<>();
        final Object nothing = new Object();

        private void put(EObject object, Object value) {
            elementMap.put(object, value);
        }
        private Object get(EObject object) {
            return elementMap.get(object);
        }

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            doSwitch(object.getDefinitions());

            put(object, get(object.getDefinitions()));
            return nothing;
        }

        @Override
        public Object caseDefinitions(Definitions object) {
            ModelInstance modelInstance = new ModelInstance();
            for (RootElement modelNode : object.getRootElements()) {
                doSwitch(modelNode);
                if (modelNode instanceof DataStore) {
                    modelInstance.addDataStoreValue((DataStore) modelNode, (DataStoreValue)get(modelNode));
                    continue;
                }
                RootElementInstance instance = (RootElementInstance) get(modelNode);
                if (instance == null) continue;
                //if the instance is not part of the collaboration then add it
                //else the collaboration instance will contain it
                if (processInCollaborationMap.get(instance) == null) {
                    instance.setParent(modelInstance);
                    modelInstance.addRootElementValue(modelNode, instance);
                }
            }
            if (modelInstance.getRootElementValueMap().isEmpty()) {
                return nothing;
            }
            put(object, modelInstance);
            return nothing;
        }

        @Override
        public Object caseProcess(Process object) {
            ProcessInstance processInstance = processInstanceMap.get(object);
            if (processInstance != null) {
                return nothing;
            }
            processInstance = new ProcessInstance(object);

            //TODO: create property instances
            //TODO: create data-object instances

            for (FlowElement flowElement : object.getFlowElements()) {
                if (!(flowElement instanceof StartEvent)) {
                    continue;
                }
                doSwitch(flowElement);
                Set<Token> tokens = (Set<Token>) get(flowElement);
                if (tokens != null && !tokens.isEmpty()) {
                    processInstance.addAll(tokens);
                }
            }
            if (processInstance.getTokens().isEmpty()) {
                return nothing;
            }

            processInstanceMap.put(object, processInstance);
            put(object, processInstance);
            return nothing;
        }

        @Override
        public Object caseCollaboration(Collaboration object) {
            CollaborationInstance collaborationInstance = new CollaborationInstance(object);
            for (Participant participant : object.getParticipants()) {
                doSwitch(participant);
                ProcessInstance processInstance = (ProcessInstance) get(participant);
                if (processInstance == null) continue;
                processInstance.setParent(collaborationInstance);
                collaborationInstance.putProcessInstance(participant.getProcessRef(), processInstance);
                processInCollaborationMap.put(processInstance, object);
            }
            if (collaborationInstance.getProcesses().isEmpty()) {
                //if no process instances in the collaboration we do not need its instance
                return nothing;
            }
            put(object, collaborationInstance);
            return nothing;
        }

        @Override
        public Object caseParticipant(Participant object) {
            //ignore multiplicities for now, just return the process instance
            doSwitch(object.getProcessRef());
            put(object, get(object.getProcessRef()));
            return nothing;
        }

        @Override
        public Object caseStartEvent(StartEvent object) {
            //only processes with start event can be instantiated at top-level
            Set<Token> tokenList = new HashSet<>();

            for (SequenceFlow sequenceFlow : object.getOutgoing()) {
                doSwitch(sequenceFlow);
                tokenList.add((Token) get(sequenceFlow));
            }

            put(object, tokenList);
            return nothing;
        }

        @Override
        public Object caseSequenceFlow(SequenceFlow object) {
            ProcessToken token = new ProcessToken();
            token.setPosition(object);

            put(object, token);
            return nothing;
        }
    }
}
