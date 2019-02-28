package plug.bpmn2.examples;

import org.eclipse.bpmn2.*;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.util.Bpmn2Switch;
import org.eclipse.emf.ecore.EObject;

public class SimpleEvaluator {

    public void evaluate(EObject obj) {
        Visitor v = new Visitor();
        v.doSwitch(obj);
    }

    class Visitor extends Bpmn2Switch<Object> {
        private final Object nonNullTag = new Object();

        @Override
        public Object caseDocumentRoot(DocumentRoot object) {
            defaultCase(object);
            return doSwitch(object.getDefinitions());
        }

        @Override
        public Object caseDefinitions(Definitions object) {
            defaultCase(object);
            for (RootElement rE : object.getRootElements()) {
                doSwitch(rE);
            }
            return nonNullTag;
        }

        @Override
        public Object caseProcess(Process object) {
            defaultCase(object);
            for (FlowElement fE : object.getFlowElements()) {
                doSwitch(fE);
            }
            return nonNullTag;
        }

        @Override
        public Object defaultCase(EObject object) {
            System.out.println(object.eClass().getName());
            return super.defaultCase(object);
        }

    }
}
