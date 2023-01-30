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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class FAAmnesicExploration {

    static BPMN2Semantics semantics = new BPMN2SemanticsNaive();

    static public boolean registerState(BPMN2ExecutionState state, Set<Integer> known, int cap) {
        int hash = Math.abs(state.hashCode()) % (cap*10);
        return known.add(hash);
    }

    static public void pocExploration(
            BPMN2ProcessedModel model,
            boolean bfs, int cap,
            DANaiveExploration.NextFunction next
    ) {
        Set<Integer> known = new HashSet<>();
        LinkedList<BPMN2ExecutionState> toSee = new LinkedList<>();

        BPMN2ExecutionState initial = new BPMN2ExecutionState(new int[0]);
        BPMN2FlowAction initialisation = model.getFlowActionPool().getFlowAction(0);
        initial = semantics.execute(initial, initialisation).stream().findAny().get();
        registerState(initial, known, cap);
        toSee.add(initial);

        LocalDateTime startTime = LocalDateTime.now();
        int count = 0;
        int maxWidth = 0;
        int maxToSee = 0;
        int maxTokens = 0;
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
                    if (registerState(target, known, cap)) toSee.addLast(target);
                }
            }
            if (width > maxWidth) maxWidth = width;
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
                + (extraTokenCount > 0 ? "+" + extraTokenCount : ""));
    }

    static public void main(String[] args) {
        System.out.println("Loading ...");

        DocumentRoot documentRoot = BPMN2EmfUtils.getDocumentRoot(ALoadingFiles.ONE_WAY_FILE_PATH);
        BPMN2ProcessedModel model = new BPMN2ProcessedModel(documentRoot);

        System.out.println("Exploring ...");

        int cap = 100000;
        pocExploration(model, true, cap, EAFlowCompletionExploration::flowCompletionNext);
        pocExploration(model, false, cap, EAFlowCompletionExploration::flowCompletionNext);

    }

}
