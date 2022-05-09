import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class used to manage a {@code Minimum Heap} of {@code Generic Type}
 */
public class MinimumHeap<T> {

    private String RED = "\033[1;31m";
    private String NC = "\033[0m";

    private ArrayList<T> h = null;
    private Comparator<? super T> comparator = null;
    private HeapOperations<T> operations = null;

    /**
     * @param i index of the element whose parent you want to know
     * @return the index of the parent
     */
    private int indexParent(int i) {
        return (i / 2);
    }

    /**
     * @param i index of the element whose left you want to know
     * @return the index of the left
     */
    private int indexLeft(int i) {
        return ((2 * i) <= this.h.size() - 1) ? (2 * i) : i;
    }

    /**
     * @param i index of the element whose right you want to know
     * @return the index of the right
     */
    private int indexRight(int i) {
        return (((2 * i) + 1) <= this.h.size() - 1) ? ((2 * i) + 1) : i;
    }

    /**
     * @param i1 index of the first element
     * @param i2 index of the second element
     */
    private void swap(int i1, int i2) {
        if (i1 != i2) {
            T tmp = this.h.get(i1);
            this.h.set(i1, this.h.get(i2));
            this.h.set(i2, tmp);
        }
    }

    /**
     * Calculate the minimum value between the element in index {@code i1} and
     * {@code i2}
     * 
     * @param i1 index first element
     * @param i2 index second element
     * @return the index of the minimum element
     */
    private int min(int i1, int i2) {
        return (((this.comparator).compare(this.h.get(i1), this.h.get(i2)) <= 0) ? i1 : i2);
    }

    /**
     * @param comparator used to compare two {@code Generic Type} elements
     * @param sentinel   element used to insert at the begin of the
     *                   {@code Minimum Heap}
     * @param operations interface with all the operations on the
     *                   {@code Minimum Heap}
     */
    public MinimumHeap(Comparator<? super T> comparator, T sentinel, HeapOperations<T> operations) {
        this.h = new ArrayList<>();
        this.comparator = comparator;
        this.operations = operations;
        this.h.add(sentinel);
    }

    /**
     * @param i index of the element whose parent you want to know
     * @return the element at the parent position on success
     * @throws IndexOutOfBoundsException on fail
     */
    public T parent(int i) throws IndexOutOfBoundsException {
        if (i >= 1 && i < this.h.size())
            return this.h.get((i / 2));
        else
            throw new IndexOutOfBoundsException(
                    RED + "\n\tparent(index):\n" + NC +
                            "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
                            "\t\t'index' given: " + i + "");
    }

    /**
     * @param i index of the element whose left you want to know
     * @return the element at the left position on success
     * @throws IndexOutOfBoundsException on fail
     */
    public T left(int i) throws IndexOutOfBoundsException {
        if (i >= 1 && i < this.h.size()) {
            return this.h.get(((2 * i) <= this.h.size() - 1) ? (2 * i) : i);
        } else
            throw new IndexOutOfBoundsException(
                    RED + "\n\tleft(index):\n" + NC +
                            "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
                            "\t\t'index' given: " + i + "");
    }

    /**
     * @param i index of the element whose right you want to know
     * @return the element at the right position on success
     * @throws IndexOutOfBoundsException on fail
     */
    public T right(int i) throws IndexOutOfBoundsException {
        if (i >= 1 && i < this.h.size()) {
            return this.h.get((((2 * i) + 1) <= this.h.size() - 1) ? ((2 * i) + 1) : i);
        } else
            throw new IndexOutOfBoundsException(
                    RED + "\n\tright(index):\n" + NC +
                            "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
                            "\t\t'index' given: " + i + "");
    }

    /**
     * @return the size of the {@code Minimum Heap}
     *         - The sentinel element is not included in the size
     */
    public int size() {
        return this.h.size() - 1;
    }

    /**
     * Insert an element into the {@code Minimum Heap}
     * 
     * @param el element to insert
     * @throws NullPointerException      when the element is {@code null}
     * @throws IndexOutOfBoundsException on fail
     * @see #parent(int)
     */
    public void insertHeap(T el) throws NullPointerException, IndexOutOfBoundsException {
        if (el == null)
            throw new NullPointerException(
                    RED + "\n\tinsertHeap(element):\n" + NC +
                            "\t\t'element' must be not NULL");

        int p = this.h.size();
        this.h.add(el);
        try {
            while (p > 1 && (this.comparator).compare(this.h.get(p), parent(p)) < 0) {
                swap(p, indexParent(p));
                p = indexParent(p);
            }
        } catch (IndexOutOfBoundsException exc) {
            System.out.println(exc);
            throw exc;
        }

    }

    /**
     * Makes the {@code Minimum Heap} the tree with root element at index {@code i}
     * since that,
     * subtree {@code left(i)} and {@code right(i)} are {@code Minimum Heap}
     * 
     * @param i index where to start {@code heapify}
     * @see #min(int, int)
     * @see #swap(int, int)
     */
    private void heapify(int i) {
        int m = min(i, min(indexLeft(i), indexRight(i)));
        if (m != i) {
            swap(m, i);
            heapify(m);
        }
    }

    /**
     * Extract the root element, then rebuild the {@code Minimum Heap}
     * 
     * @throws IndexOutOfBoundsException on an empty {@code Heap}
     */
    public void extractHeap() throws IndexOutOfBoundsException {
        if (this.h.size() <= 1)
            throw new IndexOutOfBoundsException(
                    RED + "\n\textractHeap():\n" + NC +
                            "\t\tCannot call extractHeap() if the heap size is less than 1");

        swap(1, this.h.size() - 1);
        this.h.remove(this.h.size() - 1);

        if (this.h.size() > 1)
            heapify(1);
    }

    /**
     * Decrease the element at index {@code i} of {@code howMuch} value
     * 
     * @param i       index of the element to decrease
     * @param howMuch value to know how much to decrease the element at index
     *                {@code i}.
     * @throws IndexOutOfBoundsException when the index {@code i} is out of size
     * @throws NullPointerException      when {@code howMuch} is {@code null}
     */
    public void decreaseHeap(int i, T howMuch) throws IndexOutOfBoundsException, NullPointerException {
        if (i < 1 || i >= this.h.size())
            throw new IndexOutOfBoundsException(
                    RED + "\n\tdecrease(index, howMuch)\n" + NC +
                            "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
                            "\t\t'index' given: " + i + "");

        if (howMuch == null)
            throw new NullPointerException(
                    RED + "\n\tdecrease(index, howMuch, op)\n" + NC +
                            "\t\t'howMuch' must be != null");

        T x = operations.sub(this.h.get(i), howMuch);

        this.h.set(i, x);

        // move the decremented element towards the root by checking if now is less than
        // the parent, if so swap the element with his parent, than repeat until end
        while ((this.comparator).compare(this.h.get(i), this.h.get(indexParent(i))) < 0) {
            // System.out.println("swap(" + i + ", " + indexParent(i) + ")");
            swap(i, indexParent(i));
            // System.out.println(toString());
            i = indexParent(i);
        }

        // if the element cannot go towards the root anymore, check if it can go down
        if ((this.comparator).compare(this.h.get(i), this.h.get(indexParent(i))) >= 0) {
            heapify(i);
        }
    }

    @Override
    public String toString() {
        return this.h.toString();
    }
}
