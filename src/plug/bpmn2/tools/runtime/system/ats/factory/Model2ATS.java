package plug.bpmn2.tools.runtime.system.ats.factory;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.emf.ecore.EObject;
import plug.bpmn2.tools.BPMNModelHandler;
import plug.bpmn2.tools.runtime.system.ats.BaseElementATS;
import plug.bpmn2.tools.runtime.system.ats.GlobalATS;

import java.util.Set;

public class Model2ATS {

    private GlobalATS result;;

    public GlobalATS get(BPMNModelHandler modelHandler) {
        result = new GlobalATS(modelHandler);
        BaseElementATS emptyATS = result.getEmptyScopeATS().newSubSystem(
                "empty",
                (s, rs) -> s.isEmpty()
                );

        Set<EObject> rootElements = modelHandler.ownership.getFloorSet(0);
        for (EObject rootElement : rootElements) {
            BaseElement baseElement = (BaseElement) rootElement;

        }
        return result;
    }



}
