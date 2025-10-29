package dataStructures;

import dataStructures.exceptions.*;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

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
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        this.head = null;
        this.tail = null;
        this.currentSize = 0;
        this.comparator = comparator;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        if(this.isEmpty()) { throw new NoSuchElementException(); }
        return this.head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
        if(this.isEmpty()) { throw new NoSuchElementException(); }
        return this.tail.getElement();
    }

    private DoublyListNode<E> getNode(E element){
        if(this.tail != null && this.tail.getElement().equals(element)) {
            return this.tail;
        }
        DoublyListNode<E> cur = this.head;
        while(cur != null && !(element.equals(cur.getElement()))) {
            cur = cur.getNext();
        }
        return cur;
    }

    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     */
    public E get(E element) {
        DoublyListNode<E> node = this.getNode(element);
        if(node == null){ return null; }
        return node.getElement();
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return this.getNode(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
        if(this.isEmpty()) {
            DoublyListNode<E> newNode = new DoublyListNode<>(element);
            this.head = newNode;
            this.tail = newNode;
            this.currentSize++;
            return;
        }
        if(comparator.compare(this.tail.getElement(), element) <= 0) {
            this.addLast(element);
            return;
        }
        DoublyListNode<E> cur = this.head;
        while(cur != null && comparator.compare(cur.getElement(), element) <= 0) {
            cur = cur.getNext();
        }
        if(cur == this.head) {
            this.addFirst(element);
            return;
        }
        if(cur == null) {
            this.addLast(element);
            return;
        }
        DoublyListNode<E> newNode = new DoublyListNode<>(element, cur.getPrevious(), cur);
        cur.getPrevious().setNext(newNode);
        cur.setPrevious(newNode);
        this.currentSize++;
    }

    private void addLast(E element){
        this.tail.setNext(new DoublyListNode<>(element, this.tail, null));
        this.tail = this.tail.getNext();
        this.currentSize++;
    }

    private void addFirst(E element) {
        this.head.setPrevious(new DoublyListNode<>(element, null, this.head));
        this.head = this.head.getPrevious();
        this.currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
        if(this.isEmpty()) { return null; }
        if(this.tail.getElement().equals(element)) {
            return this.removeLast();
        }
        if(this.head.getElement().equals(element)) {
            return this.removeFirst();
        }
        DoublyListNode<E> toRemove = this.getNode(element);
        if(toRemove == null) { return null; }
        E ele = toRemove.getElement();
        toRemove.getNext().setPrevious(toRemove.getPrevious());
        toRemove.getPrevious().setNext(toRemove.getNext());
        toRemove.setElement(null);
        this.currentSize--;
        return ele;
    }

    private E removeLast( ) {
        if(size() <= 0) { throw new NoSuchElementException(); }
        E element = this.tail.getElement();
        this.tail = this.tail.getPrevious();
        if(this.tail == null) { this.head = null; } else { this.tail.setNext(null); }
        currentSize--;
        return element;
    }

    private E removeFirst( ) {
        if(size() <= 0) { throw new NoSuchElementException(); }
        E element = this.head.getElement();
        this.head = this.head.getNext();
        if(this.head == null) { this.tail = null; } else { this.head.setPrevious(null); }
        currentSize--;
        return element;
    }

    /*public void inverse() {
        DoublyListNode<E> cur = this.head;
        while(cur != null) {
            DoublyListNode<E> oldNext = cur.getNext();
            cur.setNext(cur.getPrevious());
            cur.setPrevious(oldNext);
            cur = oldNext;
        }
        DoublyListNode<E> oldHead = this.head;
        this.head = this.tail;
        this.tail = oldHead;
    }*/
}