package obp2.bpmn2.plugin;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.model.propositions.ActionProps;
import obp2.bpmn2.model.propositions.ConfigurationProps;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.runtime.core.IAtomicPropositionsEvaluator;
import obp2.runtime.core.ILanguageModule;
import org.apache.commons.jexl3.*;

public class BPMN2Evaluator implements IAtomicPropositionsEvaluator<BPMN2ExecutionState, BPMN2FlowAction, Void> {

    private final BPMN2ProcessedModel model;
    private ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> module;
    private JexlExpression expressions[];
    private final JexlEngine jexl = new JexlBuilder().create();

    public BPMN2Evaluator(BPMN2ProcessedModel model) {
        this.model = model;
    }

    @Override
    public ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> getModule() {
        return module;
    }

    @Override
    public void setModule(ILanguageModule<BPMN2ExecutionState, BPMN2FlowAction, Void> module) {
        this.module = module;
    }

    @Override
    public int[] registerAtomicPropositions(String[] atomicPropositions) throws Exception {
        expressions = new JexlExpression[atomicPropositions.length];
        int indices[] = new int[atomicPropositions.length];

        for (int i = 0; i < atomicPropositions.length; i++) {
            expressions[i] = jexl.createExpression(atomicPropositions[i]);
            indices[i] = i;
        }

        return indices;
    }

    @Override
    public boolean[] getAtomicPropositionValuations(BPMN2ExecutionState source, BPMN2FlowAction fireable, Void output, BPMN2ExecutionState target) {

        // Create a context and add data
        ConfigurationProps s = new ConfigurationProps(model, source);
        ActionProps a = new ActionProps(model, fireable);
        ConfigurationProps t = new ConfigurationProps(model, target);

        JexlContext context = new ClassAwareContext();
        context.set("state", s);
        context.set("source", s);
        context.set("action", a);
        context.set("target", t);

        return evaluateOn(context);
    }

    boolean[] evaluateOn(JexlContext context) {
        // Now evaluate the expression, getting the result
        boolean results[] = new boolean[expressions.length];
        for (int i=0; i<results.length; i++) {
            results[i] = (boolean)expressions[i].evaluate(context);
        }

        return results;
    }

    private static class ClassAwareContext extends MapContext {

        @Override
        public boolean has(String name) {
            try {
                if (!super.has(name)) {
                    Class.forName(name);
                }
                return true;
            } catch (ClassNotFoundException xnf) {
                return false;
            }
        }

        @Override
        public Object get(String name) {
            try {
                Object found = super.get(name);
                if (found == null && !super.has(name)) {
                    found = Class.forName(name);
                }
                return found;
            } catch (ClassNotFoundException xnf) {
                return null;
            }
        }
    }

}
