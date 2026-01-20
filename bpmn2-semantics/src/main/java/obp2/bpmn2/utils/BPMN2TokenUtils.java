package obp2.bpmn2.utils;

import obp2.bpmn2.model.token.TokenPool;
import obp2.bpmn2.semantics.BPMN2ExecutionState;
import obp2.bpmn2.semantics.BPMN2ExecutionStep;
import org.eclipse.bpmn2.CallActivity;

import java.util.List;
import java.util.Set;

public class BPMN2TokenUtils {

    static public boolean callStackEquals(List<CallActivity> cs1, List<CallActivity> cs2) {
        if (cs1.size() != cs2.size()) return false;
        for (int i = 0; i < cs1.size(); i++) {
            CallActivity ca1 = cs1.get(i);
            CallActivity ca2 = cs2.get(i);
            if (ca1 != ca2) return false;
        }
        return true;
    }

    static public int[] toArray(List<Integer> integerList) {
        int[] array = new int[integerList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = integerList.get(i);
        }
        return array;
    }

    static private void removePositions(int[] oldTokens, int[] positionsToRemove, int[] newTokens) {
        int newTokensIndex = 0;
        int positionsToRemoveIndex = 0;
        for (int oldTokensIndex = 0; oldTokensIndex < oldTokens.length; oldTokensIndex++) {
            if (oldTokensIndex == positionsToRemove[positionsToRemoveIndex]) {
                positionsToRemoveIndex++;
                continue;
            }
            newTokens[newTokensIndex] = oldTokens[oldTokensIndex];
            newTokensIndex++;
        }
    }

    static public boolean hasToken(int[] tokens, int token) {
        for (int presentToken : tokens) if (token == presentToken) return true;
        return false;
    }

    static private void addTokens(int[] tokensToAdd, int startIndex, int[] newTokens) {
        for (int offsetIndex = 0; offsetIndex < tokensToAdd.length; offsetIndex++) {
            newTokens[startIndex + offsetIndex] = tokensToAdd[offsetIndex];
        }
    }

    static public int[] removePositionsAndAddTokens(int[] oldTokens, int[] positionsToRemove, int[] tokensToAdd) {
        int newSize = oldTokens.length - positionsToRemove.length + tokensToAdd.length;
        int[] newTokens = new int[newSize];
        // TODO
        return newTokens;
    }

    static public int[] produceUnorderedNewTokens(BPMN2ExecutionState source, BPMN2ExecutionStep step) {
        int additionStartIndex = source.tokens.length - step.tokenPositionsToRemove.length;
        int newTokensLength = additionStartIndex + step.tokenIndexesToAdd.length;
        int[] newTokens = new int[newTokensLength];
        removePositions(source.tokens, step.tokenPositionsToRemove, newTokens);
        addTokens(step.tokenIndexesToAdd, additionStartIndex, newTokens);
        return newTokens;
    }

}
