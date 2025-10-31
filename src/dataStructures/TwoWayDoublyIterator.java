package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class TwoWayDoublyIterator<E> extends DoublyIterator<E>
        implements TwoWayIterator<E> {

    /**
     * Node with the last element in the iteration.
     */
    private DoublyListNode<E> lastNode;
    /**
     * Node with the previous element in the iteration.
     */
    DoublyListNode<E> prevToReturn;

    /**
     * DoublyLLIterator constructor
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     *
     * @param first - Node with the first element of the iteration
     * @param last  - Node with the last element of the iteration
     */
    public TwoWayDoublyIterator(DoublyListNode<E> first, DoublyListNode<E> last) {
        super(first);
        this.lastNode = last;
        this.prevToReturn = last;
    }

    /**
     * Returns true if previous would return an element
     * rather than throwing an exception.
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return true iff the iteration has more elements in the reverse direction
     */
    public boolean hasPrevious( ) {
        return this.prevToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next( ){
        if(!this.hasNext()) { throw new NoSuchElementException(); }
        E element = this.nextToReturn.getElement();
        this.prevToReturn = this.nextToReturn.getPrevious();
        this.nextToReturn = this.nextToReturn.getNext();
        return element;
    }

    /**
     * Returns the previous element in the iteration.
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return previous element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E previous( ) {
        if(!this.hasPrevious()) { throw new NoSuchElementException(); }
        E element = this.prevToReturn.getElement();
        this.nextToReturn = this.prevToReturn.getNext();
        this.prevToReturn = this.prevToReturn.getPrevious();
        return element;
    }

    /**
     *
     * Restarts the iteration in the reverse direction.
     * After fullForward, if iteration is not empty,
     * previous will return the last element
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     */
    public void fullForward() {
        this.nextToReturn = null;
        this.prevToReturn = this.lastNode;
    }

    /**
     * Restart the iterator
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     */
    public void rewind() {
        this.prevToReturn = null;
        super.rewind();
    }
}
