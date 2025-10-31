package dataStructures;

import java.io.Serial;
import java.io.Serializable;

/**
 * Double List Node Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class DoublyListNode<E> implements Serializable {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Element stored in the node.
     */
    private E element;

    /**
     * (Pointer to) the previous node.
     */
    private DoublyListNode<E> previous;

    /**
     * (Pointer to) the next node.
     */
    private DoublyListNode<E> next;

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @param theElement - The element to be contained in the node
     * @param thePrevious - the previous node
     * @param theNext - the next node
     */
    public DoublyListNode(E theElement, DoublyListNode<E> thePrevious,
                          DoublyListNode<E> theNext ) {
        this.element = theElement;
        this.previous = thePrevious;
        this.next = theNext;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @param theElement to be contained in the node
     */
    public DoublyListNode(E theElement ) {
        this.element = theElement;
        this.next = null;
        this.previous = null;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return the element contained in the node
     */
    public E getElement( ) {
        return element;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return the previous node
     */
    public DoublyListNode<E> getPrevious( ) {
        return previous;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return the next node
     */
    public DoublyListNode<E> getNext( ) {
        return next;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @param newElement - New element to replace the current element
     */
    public void setElement( E newElement ) {
        this.element = newElement;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @param newPrevious - node to replace the current previous node
     */
    public void setPrevious( DoublyListNode<E> newPrevious ) {
        this.previous = newPrevious;
    }

    /**
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @param newNext - node to replace the next node
     */
    public void setNext( DoublyListNode<E> newNext ) {
        this.next = newNext;
    }
}