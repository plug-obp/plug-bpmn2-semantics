package plug.bpmn2.semantics.state;

import org.junit.Test;
import plug.bpmn2.semantics.state.utils.BPMNRuntimeEquals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BPMNRuntimeEqualsTest {

    @Test
    public void childrenEquals() {

        List<Integer> list1 = new LinkedList<>();
        List<Integer> list2 = new LinkedList<>();

        Collections.addAll(list1, 0, 1, 2);
        Collections.addAll(list2, 0, 1, 2);
        assertTrue(BPMNRuntimeEquals.childrenEquals(list1, list2));
        list1.clear();
        list2.clear();

        Collections.addAll(list1, 0, 1 ,2);
        Collections.addAll(list2, 2, 1, 0);
        assertTrue(BPMNRuntimeEquals.childrenEquals(list1, list2));
        list1.clear();
        list2.clear();

        Collections.addAll(list1, 0, 1 ,2);
        Collections.addAll(list2, 2, 1, 0, 0);
        assertFalse(BPMNRuntimeEquals.childrenEquals(list1, list2));
        list1.clear();
        list2.clear();

        Collections.addAll(list1, 0, 0, 1 ,2);
        Collections.addAll(list2, 2, 1, 1, 0);
        assertFalse(BPMNRuntimeEquals.childrenEquals(list1, list2));
        list1.clear();
        list2.clear();

        Collections.addAll(list1, 0, 0, 1 ,2);
        Collections.addAll(list2, 0, 1, 2, 0);
        assertTrue(BPMNRuntimeEquals.childrenEquals(list1, list2));
        list1.clear();
        list2.clear();
    }
}