package plug.bpmn2.interpretation.tools.analysis.resource;

import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import plug.bpmn2.interpretation.tools.BPMNToolKit;

import java.util.*;

public class ParentMap {

    private final BPMNToolKit toolKit;
    private final Map<BaseElement, Set<BaseElement>> childrenMap;
    private final Map<BaseElement, Set<BaseElement>> parentsMap;
    private final List<Set<BaseElement>> hierarchyList;
    private final Map<BaseElement, Integer> levelMap;

    public ParentMap(BPMNToolKit toolKit) {
        this.toolKit = toolKit;
        childrenMap = new HashMap<>();
        parentsMap = new HashMap<>();
        toolKit.println(this, "", "Computing model hierarchy");
        toolKit.increaseLogDepth();
        new InternalSwitch().doSwitch(toolKit.getDocumentRoot());
        hierarchyList = computeHierarchyList();
        levelMap = computeLevelMap();
        lookForAnomalies();
        toolKit.decreaseLogDepth();
    }

    private List<Set<BaseElement>> computeHierarchyList() {
        LinkedList<Set<BaseElement>> result = new LinkedList<>();
        Set<BaseElement> remainingElements = new HashSet<>(parentsMap.keySet());
        Set<BaseElement> pastElements = new HashSet<>();
        while (!remainingElements.isEmpty()) {
            Set<BaseElement> currentElements = new HashSet<>();
            for (BaseElement baseElement : remainingElements) {
                Set<BaseElement> parents = parentsMap.get(baseElement);
                if (pastElements.containsAll(parents)) {
                    currentElements.add(baseElement);
                }
            }
            if (currentElements.isEmpty()) {
                break;
            }
            remainingElements.removeAll(currentElements);
            pastElements.addAll(currentElements);
            result.add(currentElements);
        }
        return result;
    }

    private Map<BaseElement, Integer> computeLevelMap() {
        Map<BaseElement, Integer> result = new HashMap<>();
        int level = 0;
        for (Set<BaseElement> baseElementSet : hierarchyList) {
            for (BaseElement baseElement : baseElementSet) {
                result.put(baseElement, level);
            }
            level += 1;
        }
        return result;
    }

    private void lookForAnomalies() {
        if (hierarchyList.isEmpty()) {
            toolKit.println(this, "", "Empty hierarchy");
        } else {
            Set<BaseElement> rootElementSet = hierarchyList.get(0);
            for (BaseElement rootElement : rootElementSet) {
                if ((!(rootElement instanceof FlowElementsContainer)) &&
                        (!(rootElement instanceof Collaboration)) &&
                        (!(rootElement instanceof Activity))
                ) {
                    toolKit.println(this, rootElement, "Unexpected parent-less element");
                }
            }
        }
    }

    public Set<BaseElement> getParents(BaseElement baseElement) {
        return parentsMap.computeIfAbsent(
                baseElement,
                (e) -> new HashSet<>()
        );
    }

    public Set<BaseElement> getChildren(BaseElement baseElement) {
        return childrenMap.computeIfAbsent(
                baseElement,
                (e) -> new HashSet<>()
        );
    }

    public boolean isRoot(BaseElement baseElement) {
        return getParents(baseElement).isEmpty();
    }

    public List<Set<BaseElement>> getHierarchyList() {
        return hierarchyList;
    }

    public Map<BaseElement, Integer> getLevelMap() {
        return levelMap;
    }

    private class InternalSwitch extends Bpmn2Switch<Object> {

        private final LinkedList<BaseElement> parentStack;

        public InternalSwitch() {
            parentStack = new LinkedList<>();
        }

        private void before(BaseElement baseElement) {
            if (!parentStack.isEmpty()) {
                BaseElement parent = parentStack.getLast();
                if (baseElement.eContainer() != parent) {
                    toolKit.println(this, baseElement, "Different parent than the provided eContainer");
                }
                getChildren(parent).add(baseElement);
                getParents(baseElement).add(parent);
            } else {
                getChildren(baseElement);
                getParents(baseElement);
            }
            parentStack.add(baseElement);
        }

        private void after() {
            parentStack.removeLast();
        }

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            for (RootElement rootElement : object.getDefinitions().getRootElements()) {
                doSwitch(rootElement);
            }
            return 0;
        }

        @Override
        public Object caseCollaboration(Collaboration object) {
            before(object);
            for (Participant participant : object.getParticipants()) {
                doSwitch(participant);
            }
            for (MessageFlow messageFlow : object.getMessageFlows()) {
                doSwitch(messageFlow);
            }
            for (MessageFlowAssociation messageFlowAssociation : object.getMessageFlowAssociations()) {
                doSwitch(messageFlowAssociation);
            }
            after();
            return 0;
        }

        @Override
        public Object caseFlowElementsContainer(FlowElementsContainer object) {
            before(object);
            for (FlowElement flowElement : object.getFlowElements()) {
                doSwitch(flowElement);
            }
            after();
            return 0;
        }

        @Override
        public Object caseActivity(Activity object) {
            before(object);
            after();
            return 0;
        }

        @Override
        public Object caseSubProcess(SubProcess object) {
            caseActivity(object);
            caseFlowElementsContainer(object);
            return 0;
        }

        @Override
        public Object caseChoreography(Choreography object) {
            caseCollaboration(object);
            caseFlowElementsContainer(object);
            return 0;
        }

        @Override
        public Object caseParticipant(Participant object) {
            Process process = object.getProcessRef();
            if (process != null) {
                doSwitch(object.getProcessRef());
            } else {
                toolKit.println(this, object, "Participant with null process reference");
            }
            return 0;
        }

        @Override
        public Object caseMessageFlow(MessageFlow object) {
            before(object);
            Message message = object.getMessageRef();
            if (message != null) {
                doSwitch(object.getMessageRef());
            } else {
                toolKit.println(this, object, "MessageFlow with null Message reference");
            }
            after();
            return 0;
        }

        @Override
        public Object caseMessageFlowAssociation(MessageFlowAssociation object) {
            before(object);
            MessageFlow messageFlow = object.getInnerMessageFlowRef();
            if (messageFlow != null) {
                doSwitch(messageFlow);
            } else {
                toolKit.println(this, object, "MessageFlowAssociation with null MessageFlow reference");
            }
            messageFlow = object.getOuterMessageFlowRef();
            if (messageFlow != null) {
                doSwitch(messageFlow);
            } else {
                toolKit.println(this, object, "MessageFlowAssociation with null MessageFlow reference");
            }
            after();
            return 0;
        }

        @Override
        public Object caseBaseElement(BaseElement object) {
            before(object);
            after();
            return 0;
        }

    }

}
