package plug.bpmn2.plugin;

import obp2.runtime.core.TreeItem;
import obp2.runtime.core.defaults.DefaultTreeProjector;
import plug.bpmn2.model.printer.BPMNPrinterShort;
import plug.bpmn2.semantics.state.BPMNRuntimeInstance;
import plug.bpmn2.semantics.state.BPMNRuntimeState;
import plug.bpmn2.semantics.state.instance.FlowElementsContainerInstance;
import plug.bpmn2.semantics.state.instance.data.Token;
import plug.bpmn2.semantics.transition.Transition;

import java.util.LinkedList;
import java.util.List;

public class BPMNRuntimeView
        extends DefaultTreeProjector<BPMNRuntimeState, Transition, Void> {

    private final BPMNPrinterShort printer = new BPMNPrinterShort();

    public TreeItem projectInstance(BPMNRuntimeInstance instance) {
        List<TreeItem> featuresList = new LinkedList<>();
        if (instance.getChildInstanceList().size() > 0) {
            List<TreeItem> childInstanceList = new LinkedList<>();
            for (BPMNRuntimeInstance childInstance : instance.getChildInstanceList()) {
                TreeItem childInstanceItem = projectInstance(childInstance);
                childInstanceList.add(childInstanceItem);
            }
            featuresList.add(new TreeItem("Child instances", childInstanceList));
        }
        if (instance instanceof FlowElementsContainerInstance) {
            FlowElementsContainerInstance containerInstance = (FlowElementsContainerInstance) instance;
            List<TreeItem> tokenItemList = new LinkedList<>();
            for (Token token : containerInstance.getTokenSet()) {
                TreeItem tokenItem = new TreeItem(printer.getShortString(token.getBaseElement()));
                tokenItemList.add(tokenItem);
            }
            featuresList.add(new TreeItem("Tokens", tokenItemList));
        }
        return new TreeItem(printer.getShortString(instance.getBaseElement()), featuresList);
    }

    public TreeItem projectConfiguration(BPMNRuntimeState configuration) {
        if (configuration == null) return TreeItem.empty;
        List<TreeItem> instancesItemList = new LinkedList<>();
        for (BPMNRuntimeInstance instance : configuration.getRootInstanceList()) {
            instancesItemList.add(projectInstance(instance));
        }
        TreeItem instancesItem = new TreeItem("instances", instancesItemList);
        TreeItem configurationItem = new TreeItem("BPMN Configuration", instancesItem);
        return configurationItem;
    }

    public TreeItem projectFireable(Transition action) {
        return new TreeItem(action.toString());
    }

    public TreeItem projectPayload(Void output) {
        return TreeItem.empty;
    }
}
