import java.util.Comparator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MinimumHeapTestsInteger {

    class CompareInteger implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    private Integer i1, i2, i3, sentinel;
    private MinimumHeap<Integer> heapInteger;
    private HeapOperations<Integer> operations;

    @Before
    public void createMinimumHeap() {
        sentinel = Integer.MIN_VALUE;
        i1 = 4;
        i2 = 2;
        i3 = 3;
        operations = new HeapOperations<>() {
            public Integer sum(Integer v1, Integer v2) {
                return v1 + v2;
            }

            public Integer sub(Integer v1, Integer v2) {
                return v1 - v2;
            }
        };
        heapInteger = new MinimumHeap<>(new CompareInteger(), sentinel, operations);
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, heapInteger.size());
    }

    @Test(expected = NullPointerException.class)
    public void testNullExceptionThrown() {
        heapInteger.insertHeap(null);
    }

    @Test
    public void testInsertOneEl() {
        heapInteger.insertHeap(i1);
        assertEquals(1, heapInteger.size());
    }

    @Test
    public void testInsertTwoEl() {
        heapInteger.insertHeap(i1);
        heapInteger.insertHeap(i2);
        assertEquals(2, heapInteger.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testExtractFromEmpty() {
        heapInteger.extractHeap();
    }

    @Test
    public void testExtractFromSizeOfThree() {
        heapInteger.insertHeap(i1);
        heapInteger.insertHeap(i2);
        heapInteger.insertHeap(i3);
        heapInteger.extractHeap();
        assertEquals(2, heapInteger.size());
    }

    @Test
    public void testDecreaseFirst() {
        heapInteger.insertHeap(i1);
        heapInteger.insertHeap(i2);
        heapInteger.insertHeap(7);
        heapInteger.insertHeap(i3);
        // System.out.println(heapInteger);
        heapInteger.decreaseHeap(1, 4);
        // System.out.println(heapInteger);
    }

    @Test
    public void testDecreaseLastHeapTenEl() {
        for (Integer i = 10; i >= 0; --i) {
            heapInteger.insertHeap(i);
        }
        // System.out.println(heapInteger);
        heapInteger.decreaseHeap(heapInteger.size(), 4);
        // System.out.println(heapInteger);
    }
}