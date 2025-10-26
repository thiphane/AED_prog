package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Doubly Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class DoublyLinkedList<E> implements TwoWayList<E> {
    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList( ) {
        this.head = this.tail = null;
        this.currentSize = 0;

    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return this.currentSize <= 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return this.currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * @param element - Element to be inserted
     */
    public void addFirst( E element ) {
        if(this.head == null) {
            this.head = new DoublyListNode<>(element);
            this.tail = this.head;
        } else {
            this.head.setPrevious(new DoublyListNode<>(element, null, this.head));
            this.head = this.head.getPrevious();
        }
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     * @param element - Element to be inserted
     */
    public void addLast( E element ) {
        if(this.tail == null) {
            this.tail = new DoublyListNode<>(element);
            this.head = this.tail;
        } else {
            this.tail.setNext(new DoublyListNode<>(element, this.tail, null));
            this.tail = this.tail.getNext();
        }
        currentSize++;
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst( ) {
        if(this.isEmpty()) { throw new NoSuchElementException(); }
        assert this.head != null;
        return this.head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast( ) {
        if(this.isEmpty()) { throw new NoSuchElementException(); }
        assert this.tail != null;
        return this.tail.getElement();
    }

    private DoublyListNode<E> getNode(int position) {
        if(position >= size() || position < 0) {
            throw new InvalidPositionException();
        }
        boolean backwards = position > (this.size() / 2);
        DoublyListNode<E> cur;
        int count;
        if(backwards) {
            cur = tail;
            count = this.size() - position - 1;
        } else {
            cur = head;
            count = position;
        }
        for(int i = 0; i < count; i++) {
            if(backwards) {
                cur = cur.getPrevious();
            } else {
                cur = cur.getNext();
            }
        }
        return cur;
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get( int position ) {
        return this.getNode(position).getElement();
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf( E element ) {
        DoublyListNode<E> cur = this.head;
        int i = 0;
        while(cur != null && !cur.getElement().equals(element)) {
            cur = cur.getNext();
            i++;
        }
        if(cur == null) {
            return -1;
        }
        return i;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add( int position, E element ) {
        if(position == 0) { this.addFirst(element); return; }
        if(position == size()) { this.addLast(element); return; }
        // Add to middle
        DoublyListNode<E> newNext = this.getNode(position);
        DoublyListNode<E> node = new DoublyListNode<>(element, newNext.getPrevious(), newNext);
        newNext.setPrevious(node);
        node.getPrevious().setNext(node);
        currentSize++;
    }

    /**
     * Removes and returns the element at the first position in the list.
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst( ) {
        if(size() <= 0) { throw new NoSuchElementException(); }
        E element = this.head.getElement();
        this.head = this.head.getNext();
        if(this.head == null) { this.tail = null; } else { this.head.setPrevious(null); }
        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast( ) {
        if(size() <= 0) { throw new NoSuchElementException(); }
        E element = this.tail.getElement();
        this.tail = this.tail.getPrevious();
        if(this.tail == null) { this.head = null; } else { this.tail.setNext(null); }
        currentSize--;
        return element;
    }

    private E removeMiddle(int position) {
        // Remove middle
        DoublyListNode<E> toRemove = this.getNode(position);
        toRemove.getPrevious().setNext(toRemove.getNext());
        toRemove.getNext().setPrevious(toRemove.getPrevious());
        currentSize--;
        return toRemove.getElement();
    }

    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove( int position ) {
        if(position < 0 || position > size()) {
            throw new InvalidPositionException();
        }
        if(position == 0) { return removeFirst(); }
        if(position == size()-1) { return removeLast(); }
        return removeMiddle(position);
    }

}
