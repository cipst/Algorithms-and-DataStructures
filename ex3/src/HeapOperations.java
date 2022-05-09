/**
 * Interface used for the supported operations by the Minimum Heap
 * 
 * @author Stefano Cipolletta
 */
public interface HeapOperations<T> {

    /**
     * Sum two elements of {@code Generic Type}
     * 
     * @param o1 first element
     * @param o2 second element
     * @return {@code o2} summed to {@code o1}
     */
    T sum(T o1, T o2);

    /**
     * Subtract two elements of {@code Generic Type}
     * 
     * @param o1 first element
     * @param o2 second element
     * @return {@code o2} subtracted to {@code o1}
     */
    T sub(T o1, T o2);
}
