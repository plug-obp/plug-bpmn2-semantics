package obp2.bpmn2.pocs;

import obp2.bpmn2.model.BPMN2ProcessedModel;
import obp2.bpmn2.model.action.BPMN2FlowAction;
import obp2.bpmn2.model.token.Token;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.bpmn2.semantics.BPMN2Semantics;
import obp2.bpmn2.semantics.BPMN2SemanticsNaive;
import obp2.bpmn2.utils.BPMN2EmfUtils;
import org.eclipse.bpmn2.DocumentRoot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class DANaiveExploration {

    static BPMN2Semantics semantics = new BPMN2SemanticsNaive();

    @FunctionalInterface
    public interface NextFunction {
        BPMN2ExecutionState compute(BPMN2ExecutionState source, BPMN2FlowAction action, BPMN2ProcessedModel model);
    }

    static public BPMN2ExecutionState naiveExecution(
            BPMN2ExecutionState source,
            BPMN2FlowAction action,
            BPMN2ProcessedModel model) {
        return semantics.execute(source, action).stream().findAny().get();
    }

    static public void pocExploration(BPMN2ProcessedModel model, boolean bfs, int cap, NextFunction next) {
        Set<BPMN2ExecutionState> known = new HashSet<>();
        LinkedList<BPMN2ExecutionState> toSee = new LinkedList<>();

        BPMN2ExecutionState initial = new BPMN2ExecutionState(new int[0]);
        BPMN2FlowAction initialisation = model.getFlowActionPool().getFlowAction(0);
        initial = semantics.execute(initial, initialisation).stream().findAny().get();
        known.add(initial);
        toSee.add(initial);

        LocalDateTime startTime = LocalDateTime.now();
        int count = 0;
        int maxWidth = 0;
        int maxToSee = 0;
        int maxTokens = 0;
        int nbDeadlock = 0;
        int[] maxTokensTokens = new int[0];
        while (!toSee.isEmpty() && known.size() < cap) {
            BPMN2ExecutionState source = bfs ? toSee.removeFirst() : toSee.removeLast();
            if (source.tokens.length > maxTokens) {
                maxTokens = source.tokens.length;
                maxTokensTokens = source.tokens;
            }
            int width = 0;
            for (BPMN2FlowAction flowAction : model.getFlowActionPool().getFlowActionArray()) {
                if (source.tokens.length > 0 && semantics.canFire(source, flowAction)) {
                    width += 1;
                    BPMN2ExecutionState target = next.compute(source, flowAction, model);
                    if (known.add(target)) toSee.addLast(target);
                }
            }
            if (width > maxWidth) maxWidth = width;
            if (width == 0 && source.tokens.length > 0) nbDeadlock++;
            if (toSee.size() > maxToSee) maxToSee = toSee.size();
            count += 1;
        }

        Duration duration = Duration.between(startTime, LocalDateTime.now());

        int baseTokenCount = 0;
        for (int tokenId : maxTokensTokens) {
            Token token = model.getTokenPool().getToken(tokenId);
            if (token.getCallStack().size() == 0) baseTokenCount++;
        }
        int extraTokenCount = maxTokens - baseTokenCount;

        System.out.println((bfs ? "[BFS]" : "[DFS]")
                + count + " states, "
                + duration
                + ", maxWidth: " + maxWidth
                + ", maxFrontier: " + maxToSee
                + ", known: " + known.size()
                + ", maxTokens: " + baseTokenCount
                + (extraTokenCount > 0 ? "+" + extraTokenCount : "")
                + ", nbDeadlocks: " + nbDeadlock);
    }

    static public void pocNaiveExploration(BPMN2ProcessedModel model, boolean bfs, int cap) {
        pocExploration(model, bfs, cap, DANaiveExploration::naiveExecution);
    }

    static public void main(String[] args) {
        System.out.println("Loading ...");

        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        BPMN2ProcessedModel model = new BPMN2ProcessedModel(documentRoot);

        System.out.println("Exploring ...");

        pocNaiveExploration(model, true, 10000);
        pocNaiveExploration(model, false, 10000);

    }

}
