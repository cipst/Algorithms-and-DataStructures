import java.util.ArrayList;
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

    private Integer i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
    private MinimumHeap<Integer> heapInteger;

    @Before
    public void createMinimumHeap() {
        // sentinel = Integer.MIN_VALUE;
        i1 = 1;
        i2 = 2;
        i3 = 3;
        i4 = 4;
        i5 = 5;
        i6 = 6;
        i7 = 7;
        i8 = 8;
        i9 = 9;
        i10 = 10;
        // operations = new HeapOperations<>() {
        // public Integer sum(Integer v1, Integer v2) {
        // return v1 + v2;
        // }

        // public Integer sub(Integer v1, Integer v2) {
        // return v1 - v2;
        // }
        // };
        heapInteger = new MinimumHeap<>(new CompareInteger());
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, heapInteger.size());
    }

    // @Test(expected = MinimumHeapException.class)
    // public void testNullExceptionThrown() {
    // heapInteger.add(null);
    // }

    // @Test
    // public void testInsertOneEl() {
    //     heapInteger.add(i1);
    //     assertEquals(1, heapInteger.size());
    // }

    // @Test
    // public void testInsertTwoEl() {
    //     heapInteger.add(i1);
    //     heapInteger.add(i2);
    //     System.out.println(heapInteger);
    //     assertEquals(2, heapInteger.size());
    // }

    @Test
    public void testInsertTenEl() {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        System.out.println(heapInteger);
        assertEquals(10, heapInteger.size());
    }

    // @Test
    // public void testInsertOneElRemoveOneEl() {
    //     heapInteger.add(i1);
    //     heapInteger.remove();
    //     assertEquals(0, heapInteger.size());
    // }

    // @Test
    // public void testExtractFromSizeOfThree() {
    // heapInteger.insertHeap(i1);
    // heapInteger.insertHeap(i2);
    // heapInteger.insertHeap(i3);
    // heapInteger.extractHeap();
    // assertEquals(2, heapInteger.size());
    // }

    // @Test
    // public void testDecreaseFirst() {
    // heapInteger.insertHeap(i1);
    // heapInteger.insertHeap(i2);
    // heapInteger.insertHeap(7);
    // heapInteger.insertHeap(i3);
    // // System.out.println(heapInteger);
    // heapInteger.decreaseHeap(1, 4);
    // // System.out.println(heapInteger);
    // }

    // @Test
    // public void testDecreaseLastHeapTenEl() {
    // for (Integer i = 10; i >= 0; --i) {
    // heapInteger.insertHeap(i);
    // }
    // // System.out.println(heapInteger);
    // heapInteger.decreaseHeap(heapInteger.size(), 4);
    // // System.out.println(heapInteger);
    // }
}