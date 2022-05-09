import java.util.Comparator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MinimumHeapTestsString {

    class CompareString implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    private String s1, s2, s3, s4, s5, sentinel;
    private MinimumHeap<String> heapString;
    private HeapOperations<String> operations;

    @Before
    public void createMinimumHeap() {
        sentinel = "";
        s1 = "Paracadute";
        s2 = "Aereoplano";
        s3 = "Altopiano";
        s4 = "Parapendio";
        s5 = "Pianoforte";
        operations = new HeapOperations<>() {
            public String sum(String v1, String v2) {
                return v1.concat(v2);
            }

            public String sub(String v1, String v2) {
                return v1.contains(v2) ? v1.replaceAll(v2, "") : v1;
            }
        };
        heapString = new MinimumHeap<>(new CompareString(), sentinel, operations);
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, heapString.size());
    }

    @Test(expected = NullPointerException.class)
    public void testNullExceptionThrown() {
        heapString.insertHeap(null);
    }

    @Test
    public void testInsertOneEl() {
        heapString.insertHeap(s1);
        assertEquals(1, heapString.size());
    }

    @Test
    public void testInsertTwoEl() {
        heapString.insertHeap(s1);
        heapString.insertHeap(s2);
        assertEquals(2, heapString.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExtractFromEmpty() {
        heapString.extractHeap();
    }

    @Test
    public void testExtractFromSizeOfThree() {
        heapString.insertHeap(s1);
        heapString.insertHeap(s2);
        heapString.insertHeap(s3);
        heapString.extractHeap();
        assertEquals(2, heapString.size());
    }

    @Test
    public void testDecreaseFirst() {
        heapString.insertHeap(s3);
        heapString.insertHeap(s2);
        heapString.insertHeap("Albero");
        heapString.insertHeap(s1);
        // System.out.println(heapString);
        heapString.decreaseHeap(1, "Aereo");
        // System.out.println(heapString);
    }

    @Test
    public void testDecreaseLastHeapFiveEl() {
        heapString.insertHeap(s1);
        heapString.insertHeap(s2);
        heapString.insertHeap(s3);
        heapString.insertHeap(s4);
        heapString.insertHeap(s5);
        // System.out.println(heapString);
        heapString.decreaseHeap(heapString.size(), "Piano");
        // System.out.println(heapString);
    }
}