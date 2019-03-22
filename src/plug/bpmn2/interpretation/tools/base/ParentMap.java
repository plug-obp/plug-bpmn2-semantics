package plug.bpmn2.interpretation.tools.base;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;

import java.util.*;

public class ParentMap {

    private final Map<BaseElement, Set<BaseElement>> childrenMap;
    private final Map<BaseElement, Set<BaseElement>> parentsMap;

    public ParentMap(DocumentRoot documentRoot) {
        childrenMap = new HashMap<>();
        parentsMap = new HashMap<>();
        new InternalSwitch().doSwitch(documentRoot);
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

        private final Queue<BaseElement> parentStack;

        public InternalSwitch() {
            parentStack = new LinkedList<>();
        }

        private void before(BaseElement baseElement) {
            BaseElement parent = parentStack.peek();
            if (parent != null) {
                getChildren(parent).add(baseElement);
                getParents(baseElement).add(parent);
            }
            parentStack.add(baseElement);
        }

        private void after() {
            parentStack.remove();
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

    }

}
