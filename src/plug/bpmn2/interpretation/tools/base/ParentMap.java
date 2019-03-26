package plug.bpmn2.interpretation.tools.base;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;

import java.util.*;

public class ParentMap {

    private final Map<BaseElement, Set<BaseElement>> childrenMap;
    private final Map<BaseElement, Set<BaseElement>> parentsMap;
    private final List<Set<BaseElement>> hierarchyList;
    private final Map<BaseElement, Integer> levelMap;

    public ParentMap(DocumentRoot documentRoot) {
        childrenMap = new HashMap<>();
        parentsMap = new HashMap<>();
        new InternalSwitch().doSwitch(documentRoot);
        hierarchyList = computeHierarchyList();
        levelMap = computeLevelMap();
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

    private class InternalSwitch extends Bpmn2Switch<Object> {

        private final LinkedList<BaseElement> parentStack;

        public InternalSwitch() {
            parentStack = new LinkedList<>();
        }

        private void before(BaseElement baseElement) {
            if (!parentStack.isEmpty()) {
                BaseElement parent = parentStack.getLast();
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
                Process process = participant.getProcessRef();
                if (process != null) {
                    doSwitch(process);
                }
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
        public Object caseMessageFlow(MessageFlow object) {
            before(object);
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
