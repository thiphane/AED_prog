package dataStructures;
/**
 * Array Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {
    private E[] elems;
    private int counter;
    private int current;

    /**
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @param elems Array with elements
     * @param counter Size of array
     */
    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }

    /**
     * Time complexity: O(1).
     */
    @Override
    public void rewind() {
        this.current = 0;
    }

    /**
     * Time complexity: O(1).
     * @return if current element is the last one.
     */
    @Override
    public boolean hasNext() {
        return this.current < this.counter;
    }

    /**
     * Time complexity: O(1).
     * @return Next element in the list.
     */
    @Override
    public E next() {
        return this.elems[this.current++];
    }

}
