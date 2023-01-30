package obp2.bpmn2.model.token;

import obp2.bpmn2.utils.BPMN2TokenUtils;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.CallActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenPool {

    private final Token[] tokenArray;

    protected TokenPool(Collection<Token> tokenCollection) {
        tokenArray = new Token[tokenCollection.size()];
        int id = 0;
        for (Token token : tokenCollection) {
            token.setId(id);
            tokenArray[id] = token;
            id++;
        }
    }

    public Token[] getTokenArray() {
        return tokenArray;
    }

    public Token getToken(int id) {
        return tokenArray[id];
    }

    public int[] getTokenIds(BaseElement baseElement) {
        List<Integer> tokenList = new ArrayList<>();
        for (Token token : tokenArray) {
            if (token.getPlaceBaseElement().equals(baseElement)) {
                tokenList.add(token.getId());
            }
        }
        return BPMN2TokenUtils.toArray(tokenList);
    }

    public int[] getTokenIds(List<CallActivity> callStack) {
        List<Integer> tokenList = new ArrayList<>();
        for (Token token : tokenArray) {
            if (BPMN2TokenUtils.callStackEquals(callStack, token.getCallStack())) tokenList.add(token.getId());
        }
        return BPMN2TokenUtils.toArray(tokenList);
    }

    public int getTokenId(List<CallActivity> callStack, BaseElement baseElement) {
        int[] tokenIds = getTokenIds(baseElement);
        int lastTokenId = -1;
        int tokenCount = 0;
        for (int tokenId : tokenIds) {
            Token token = getToken(tokenId);
            if (!BPMN2TokenUtils.callStackEquals(callStack, token.getCallStack())) continue;
            lastTokenId = token.getId();
            tokenCount++;
        }
        if (tokenCount > 1) throw new IllegalStateException("More than one matching token (" + tokenCount + ")");
        return lastTokenId;
    }

}
