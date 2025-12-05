package dataStructures;

import dataStructures.exceptions.*;

public class QueueInList<E> implements Queue<E> {

    // Memory of the queue: a list.
    private List<E> list;

    /**
     * Creates a new empty queue, initializing the memory
     */
    public QueueInList( ){
        list = new SinglyLinkedList<E>();
    }

    /**
     * Returns true iff the queue contains no elements.
     * O(1)
     * @return true if queue is empty, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     * O(1)
     * @return queue size
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Inserts the specified element at the rear of the queue.
     * O(1)
     * @param element to be added into the queue
     */
    @Override
    public void enqueue(E element) {
        list.addLast(element);
    }

    /**
     * Removes and returns the element at the front of the queue.
     * O(1)
     * @return the element at the front of this queue
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E dequeue() {
        if ( this.isEmpty() ) {
            throw new EmptyQueueException();
        }
        return list.removeFirst();
    }
    /**
     * Returns the element at the front of the queue.
     * O(1)
     * @return the element at the front of this queue
     * @throws EmptyQueueException if the queue is empty
     */
    @Override
    public E peek() {
        if ( this.isEmpty() ) {
            throw new EmptyQueueException();
        }
        return list.getFirst();
    }
}
