package plug.bpmn2.dsim.tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FoldExplorationTest extends AbstractExplorationTest {

    @Override
    protected TransitionRelation createTransitionRelation() {
        return new FoldTransitionRelation(getModelHandler());
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void mutex() {
        super.mutex();
    }

    @Override
    public void enstabSpray() {
        try {
            super.enstabSpray();
        } catch (UnsupportedOperationException e) {
            assertTrue(e.getMessage().contains("SubProcess"));
            System.out.println("Unsupported Operation Exception 'SubProcess' thrown as expected");
        }
    }

    @Override
    public void parallel_gateway() {
        super.parallel_gateway();
        assertEquals(10, getSize());
    }

}