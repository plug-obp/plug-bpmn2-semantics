package plug.bpmn2.tools.runtime.system.simple.common;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import plug.bpmn2.interpretation.model.BPMNRuntimeInstance;
import plug.bpmn2.interpretation.model.BPMNRuntimeState;
import plug.bpmn2.interpretation.model.instance.ProcessInstance;
import plug.bpmn2.interpretation.model.instance.data.Token;
import plug.bpmn2.interpretation.model.instance.impl.ProcessInstanceImpl;
import plug.bpmn2.tools.BPMNModelHandler;

import java.util.Collection;
import java.util.List;

public class Elements {

    private Elements() {}

    static public void instantiateProcess(BPMNModelHandler model,
                                          BPMNRuntimeState state,
                                          BPMNRuntimeInstance scope,
                                          Process process) {
        ProcessInstance processInstance = new ProcessInstanceImpl(scope, process);
        for (FlowElement flowElement : process.getFlowElements()) {
            if (flowElement instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) flowElement;
                if (startEvent.getIncoming().isEmpty()) {
                    List<SequenceFlow> outgoings = startEvent.getOutgoing();
                    for (SequenceFlow outgoing : outgoings) {
                        Token token = model.tokens.get(outgoing);
                        processInstance.getTokenSet().add(token);
                    }
                }
            }
        }
        if (!processInstance.getTokenSet().isEmpty()) {
            Collection<BPMNRuntimeInstance> targetCollection =
                    scope == null ?
                            state.getRootInstances() :
                            scope.getChildInstanceSet();
            targetCollection.add(processInstance);
        }
    }

}
