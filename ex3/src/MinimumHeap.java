import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * Class used to manage a {@code Minimum Heap} of {@code Generic Type}
 */
public class MinimumHeap<T> {

    private ArrayList<T> array = null;
    private Comparator<? super T> comparator = null;
    private Hashtable<T, Integer> table = null;

    /**
     * @param comparator used to compare two {@code Generic Type} elements
     * @throws MinimumHeapException when {@code comparator} is {@code null}
     */
    public MinimumHeap(Comparator<? super T> comparator) throws MinimumHeapException {
        if (comparator == null)
            throw new MinimumHeapException("\nMinimumHeap(comparator): comparator must be != null");

        this.array = new ArrayList<>();
        this.comparator = comparator;
        this.table = new Hashtable<>();
    }

    /**
     * @return the size of the {@code MinimumHeap}
     */
    public int size() {
        return this.array.size();
    }

    /**
     * @param element whose parent you want to find out
     * @return the parent element if exist, the element given otherwise
     * @throws MinimumHeapException when {@code element} is {@code null}
     */
    public T parent(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nparent(element): element must be != null");

        if (!this.table.containsKey(element))
            return element;

        Integer pos = this.table.get(element);

        if (pos == 0)
            return element;

        if (pos % 2 == 0)
            return this.array.get((pos / 2) - 1);

        return this.array.get(pos / 2);
    }

    /**
     * @param element whose left child you want to find
     * @return the left child element if exist, the element given otherwise
     * @throws MinimumHeapException when {@code element} is {@code null}
     */
    public T left(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nleft(element): element must be != null");

        if (!this.table.containsKey(element))
            return element;

        Integer pos = this.table.get(element);

        if (((2 * pos) + 1) < this.array.size())
            return this.array.get((2 * pos) + 1);
        else
            return element;
    }

    /**
     * @param element whose right child you want to find
     * @return the right child element if exist, the element given otherwise
     * @throws MinimumHeapException when {@code element} is {@code null}
     */
    public T right(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nright(element): element must be != null");

        if (!this.table.containsKey(element))
            return element;

        Integer pos = this.table.get(element);

        if (((2 * pos) + 2) < this.array.size())
            return this.array.get((2 * pos) + 2);
        else
            return element;
    }

    /**
     * @param o1 an element of the {@code MinimumHeap}
     * @param o2 an element of the {@code MinimumHeap}
     * @throws MinimumHeapException when {@code o1} or {@code o2} are {@code null}
     */
    private void swap(T o1, T o2) throws MinimumHeapException {
        if (o1 == null)
            throw new MinimumHeapException("\nswap(o1, o2): o1 must be != null");

        if (o2 == null)
            throw new MinimumHeapException("\nswap(o1, o2): o2 must be != null");

        Integer i_o1 = this.table.get(o1);
        Integer i_o2 = this.table.get(o2);
        T tmp = this.array.get(i_o1);

        this.array.set(i_o1, o2);
        this.table.put(o2, i_o1);

        this.array.set(i_o2, tmp);
        this.table.put(tmp, i_o2);
    }

    /**
     * Add an {@code element} of {@code generic type} into the {@code MinimumHeap}
     * 
     * @param element to add
     * @throws MinimumHeapException when:
     *                              <ul>
     *                              <li>{@code element} is {@code null}</li>
     *                              <li>{@code element} is already into the
     *                              {@code MinimumHeap}</li>
     *                              </ul>
     */
    public void add(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nadd(element): element must be != null");

        this.array.add(element);

        if (this.table.putIfAbsent(element, this.table.size()) != null)
            throw new MinimumHeapException("\nadd(element): element is already in the MinimumHeap");

        while ((this.comparator).compare(element, parent(element)) < 0)
            swap(element, parent(element));
    }

    /**
     * @param o1 an element of the {@code MinimumHeap}
     * @param o2 an element of the {@code MinimumHeap}
     * @throws MinimumHeapException when {@code o1} or {@code o2} are {@code null}
     */
    private T min(T o1, T o2) throws MinimumHeapException {
        if (o1 == null)
            throw new MinimumHeapException("\nmin(o1, o2): o1 must be != null");

        if (o2 == null)
            throw new MinimumHeapException("\nmin(o1, o2): o2 must be != null");

        return (((this.comparator).compare(o1, o2) < 0) ? o1 : o2);
    }

    /**
     * Makes the {@code MinimumHeap} the tree with root element the {@code element}
     * 
     * @param element where to start {@code heapify}
     * @throws MinimumHeapException when {@code element} is {@code null}
     * @see #min(T, T)
     * @see #swap(T, T)
     */
    private void heapify(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nheapify(element): element must be != null");

        T m = min(element, min(left(element), right(element)));

        if ((this.comparator).compare(element, m) != 0) {
            swap(m, element);

            heapify(element);
        }
    }

    /**
     * @return the root element in the {@code MinimumHeap}
     */
    private T root() {
        return this.array.get(0);
    }

    /**
     * @return the last element in the {@code MinimumHeap}
     */
    private T last() {
        return this.array.get(this.array.size() - 1);
    }

    /**
     * Remove the root element, then rebuild the {@code MinimumHeap}
     * 
     * @throws MinimumHeapException an empty {@code MinimumHeap}
     */
    public void remove() throws MinimumHeapException {
        if (this.array.isEmpty())
            throw new MinimumHeapException("\nremove(): cannot remove the root element of an empty MinimumHeap");

        swap(root(), last());
        this.table.remove(last());
        this.array.remove(this.array.size() - 1);

        if (this.array.size() > 1)
            heapify(root());
    }

    /**
     * Change the given {@code element} with the given {@code newElement}
     * 
     * @param element    to change
     * @param newElement to change with
     * @throws MinimumHeapException when:
     *                              <ul>
     *                              <li>{@code element} is {@code null}</li>
     *                              <li>{@code newElement} is {@code null}</li>
     *                              <li>{@code element} is NOT into the
     *                              {@code MinimumHeap}</li>
     *                              </ul>
     */
    public void decrease(T element, T newElement) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\ndecrease(element, newElement): element must be != null");

        if (newElement == null)
            throw new MinimumHeapException("\ndecrease(element, newElement): newElement must be != null");

        if (!this.table.containsKey(element))
            throw new MinimumHeapException(
                    "\ndecrease(element, newElement): cannot decrease an element that is not in the MinimumHeap");

        Integer pos = this.table.get(element);
        this.table.put(newElement, pos);
        this.array.set(pos, newElement);

        // move the decremented element towards the root by checking if now is less than
        // the parent, if so swap the element with his parent, than repeat until end
        while ((this.comparator).compare(newElement, parent(newElement)) < 0)
            swap(newElement, parent(newElement));

        // if the element cannot go towards the root anymore, check if it can go down
        if ((this.comparator).compare(newElement, parent(newElement)) >= 0)
            heapify(newElement);
    }

    @Override
    public String toString() {
        return this.array.toString();
    }

    /**
     * @return the {@code MinimumHeap} as an array of {@code Object}
     */
    public Object[] toArray() {
        return array.toArray();
    }
}

// public class MinimumHeap<T> {

// private ArrayList<T> h = null;
// private Comparator<? super T> comparator = null;
// private HeapOperations<T> operations = null;

// /**
// * @param i index of the element whose parent you want to know
// * @return the index of the parent
// */
// private int indexParent(int i) {
// return (i / 2);
// }

// /**
// * @param i index of the element whose left you want to know
// * @return the index of the left
// */
// private int indexLeft(int i) {
// return ((2 * i) <= this.h.size() - 1) ? (2 * i) : i;
// }

// /**
// * @param i index of the element whose right you want to know
// * @return the index of the right
// */
// private int indexRight(int i) {
// return (((2 * i) + 1) <= this.h.size() - 1) ? ((2 * i) + 1) : i;
// }

// /**
// * @param i1 index of the first element
// * @param i2 index of the second element
// */
// private void swap(int i1, int i2) {
// if (i1 != i2) {
// T tmp = this.h.get(i1);
// this.h.set(i1, this.h.get(i2));
// this.h.set(i2, tmp);
// }
// }

// /**
// * Calculate the minimum value between the element in index {@code i1} and
// * {@code i2}
// *
// * @param i1 index first element
// * @param i2 index second element
// * @return the index of the minimum element
// */
// private int min(int i1, int i2) {
// return (((this.comparator).compare(this.h.get(i1), this.h.get(i2)) <= 0) ? i1
// : i2);
// }

// /**
// * @param comparator used to compare two {@code Generic Type} elements
// * @param sentinel element used to insert at the begin of the
// * {@code Minimum Heap}
// * @param operations interface with all the operations on the
// * {@code Minimum Heap}
// */
// public MinimumHeap(Comparator<? super T> comparator, T sentinel,
// HeapOperations<T> operations) {
// this.h = new ArrayList<>();
// this.comparator = comparator;
// this.operations = operations;
// this.h.add(sentinel);
// }

// /**
// * @param i index of the element whose parent you want to know
// * @return the element at the parent position on success
// * @throws IndexOutOfBoundsException on fail
// */
// public T parent(int i) throws IndexOutOfBoundsException {
// if (i >= 1 && i < this.h.size())
// return this.h.get((i / 2));
// else
// throw new IndexOutOfBoundsException(
// RED + "\n\tparent(index):\n" + NC +
// "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
// "\t\t'index' given: " + i + "");
// }

// /**
// * @param i index of the element whose left you want to know
// * @return the element at the left position on success
// * @throws IndexOutOfBoundsException on fail
// */
// public T left(int i) throws IndexOutOfBoundsException {
// if (i >= 1 && i < this.h.size()) {
// return this.h.get(((2 * i) <= this.h.size() - 1) ? (2 * i) : i);
// } else
// throw new IndexOutOfBoundsException(
// RED + "\n\tleft(index):\n" + NC +
// "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
// "\t\t'index' given: " + i + "");
// }

// /**
// * @param i index of the element whose right you want to know
// * @return the element at the right position on success
// * @throws IndexOutOfBoundsException on fail
// */
// public T right(int i) throws IndexOutOfBoundsException {
// if (i >= 1 && i < this.h.size()) {
// return this.h.get((((2 * i) + 1) <= this.h.size() - 1) ? ((2 * i) + 1) : i);
// } else
// throw new IndexOutOfBoundsException(
// RED + "\n\tright(index):\n" + NC +
// "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
// "\t\t'index' given: " + i + "");
// }

// /**
// * @return the size of the {@code Minimum Heap}
// * - The sentinel element is not included in the size
// */
// public int size() {
// return this.h.size() - 1;
// }

// /**
// * Insert an element into the {@code Minimum Heap}
// *
// * @param el element to insert
// * @throws IllegalArgumentException when the element is {@code null}
// * @throws IndexOutOfBoundsException on fail
// * @see #parent(int)
// */
// public void insertHeap(T el) throws IllegalArgumentException,
// IndexOutOfBoundsException {
// if (el == null)
// throw new IllegalArgumentException(
// RED + "\n\tinsertHeap(element):\n" + NC +
// "\t\t'element' must be not NULL");

// int p = this.h.size();
// this.h.add(el);
// try {
// while (p > 1 && (this.comparator).compare(this.h.get(p), parent(p)) < 0) {
// swap(p, indexParent(p));
// p = indexParent(p);
// }
// } catch (IndexOutOfBoundsException exc) {
// System.out.println(exc);
// throw exc;
// }

// }

// /**
// * Makes the {@code Minimum Heap} the tree with root element at index {@code
// i}
// * since that,
// * subtree {@code left(i)} and {@code right(i)} are {@code Minimum Heap}
// *
// * @param i index where to start {@code heapify}
// * @see #min(int, int)
// * @see #swap(int, int)
// */
// private void heapify(int i) {
// int m = min(i, min(indexLeft(i), indexRight(i)));
// if (m != i) {
// swap(m, i);
// heapify(m);
// }
// }

// /**
// * Extract the root element, then rebuild the {@code Minimum Heap}
// *
// * @throws IndexOutOfBoundsException on an empty {@code Heap}
// */
// public void extractHeap() throws IndexOutOfBoundsException {
// if (this.h.size() <= 1)
// throw new IndexOutOfBoundsException(
// RED + "\n\textractHeap():\n" + NC +
// "\t\tCannot call extractHeap() if the heap size is less than 1");

// swap(1, this.h.size() - 1);
// this.h.remove(this.h.size() - 1);

// if (this.h.size() > 1)
// heapify(1);
// }

// /**
// * Decrease the element at index {@code i} of {@code howMuch} value
// *
// * @param i index of the element to decrease
// * @param howMuch value to know how much to decrease the element at index
// * {@code i}.
// * @throws IndexOutOfBoundsException when the index {@code i} is out of size
// * @throws IllegalArgumentException when {@code howMuch} is {@code null}
// */
// public void decrease(int i, T howMuch) throws IndexOutOfBoundsException,
// IllegalArgumentException {
// if (i < 1 || i >= this.h.size())
// throw new IndexOutOfBoundsException(
// RED + "\n\tdecrease(index, howMuch)\n" + NC +
// "\t\t'index' must be >= 1 and < " + this.h.size() + "\n" +
// "\t\t'index' given: " + i + "");

// if (howMuch == null)
// throw new IllegalArgumentException(
// RED + "\n\tdecrease(index, howMuch, op)\n" + NC +
// "\t\t'howMuch' must be != null");

// T x = operations.sub(this.h.get(i), howMuch);

// this.h.set(i, x);

// // move the decremented element towards the root by checking if now is less
// than
// // the parent, if so swap the element with his parent, than repeat until end
// while ((this.comparator).compare(this.h.get(i), this.h.get(indexParent(i))) <
// 0) {
// // System.out.println("swap(" + i + ", " + indexParent(i) + ")");
// swap(i, indexParent(i));
// // System.out.println(toString());
// i = indexParent(i);
// }

// // if the element cannot go towards the root anymore, check if it can go down
// if ((this.comparator).compare(this.h.get(i), this.h.get(indexParent(i))) >=
// 0) {
// heapify(i);
// }
// }

// @Override
// public String toString() {
// return this.h.toString();
// }
// }
