package dataStructures;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class DoublyIterator<E> implements Iterator<E> {
    /**
     * Node with the first element in the iteration.
     */
    private final DoublyListNode<E> firstNode;

    /**
     * Node with the next element in the iteration.
     */
    DoublyListNode<E> nextToReturn;


    /**
     * DoublyIterator constructor
     * Time complexity: O(1).
     * @param first - Node with the first element of the iteration
     */
    public DoublyIterator(DoublyListNode<E> first) {
        this.firstNode = first;
        this.nextToReturn = first;
    }
    /**
     * Returns the next element in the iteration.
     * Time complexity: O(1).
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next( ){
        if(!this.hasNext()) { throw new NoSuchElementException(); }
        E element = this.nextToReturn.getElement();
        this.nextToReturn = this.nextToReturn.getNext();
        return element;
    }

    /**
     * Restart the iterator
     * Time complexity: O(1).
     */
    public void rewind() {
        this.nextToReturn = this.firstNode;
    }
    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * Time complexity: O(1).
     * @return true iff the iteration has more elements
     */
    public boolean hasNext( ) {
        return this.nextToReturn != null;
    }


}
