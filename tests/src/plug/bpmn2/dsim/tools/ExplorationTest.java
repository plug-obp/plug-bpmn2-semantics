package plug.bpmn2.dsim.tools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExplorationTest extends AbstractExplorationTest {

    @Override
    protected TransitionRelation createTransitionRelation() {
        return new TransitionRelation(getModelHandler());
    }

    @Override
    @Test(expected = IllegalArgumentException.class)
    public void mutex() {
        super.mutex();
    }

    @Override
    public void process_e0t0e1() {
        super.process_e0t0e1();
        assertEquals(4, getSize());
    }

    @Override
    public void process_CAS_191029() {
        super.process_CAS_191029();
        assertEquals(1, getSize());
    }

    @Override
    public void process_CAS_191030() {
        super.process_CAS_191030();
        assertEquals(1, getSize());
    }

    @Override
    public void process_CAS_191128() {
        super.process_CAS_191128();
        int size0 = 4;
        int size1 = 10;
        int size2 = 2 + (4 * 4);
        int size3 = 6;
        int size4 = 4;
        int size = size0 * size1 * size2 * size3 * size4; // 17280
        assertEquals(size, getSize());
        assertEquals(80832, getTransitionCount());
    }

    @Override
    public void process_2() {
        super.process_2();
        int size0 = 4;
        int size1 = 2 + (4 * 4);
        int size = size0 * size1; // 72
        assertEquals(size, getSize());
        assertEquals(158, getTransitionCount());
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
    public void messages_1to1() {
        super.messages_1to1();
        assertEquals(3, getSize());
    }

    @Override
    public void parallel_gateway() {
        super.parallel_gateway();
        assertEquals(12, getSize());
    }
}
