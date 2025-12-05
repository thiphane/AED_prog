package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
import dataStructures.exceptions.InvalidPositionException;

import java.io.*;

public class SinglyLinkedList<E> implements List<E>, Serializable {
    /**
     *  Node at the head of the list.
     */
    transient private SinglyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    transient private SinglyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    transient private int currentSize;
    /**
     * Constructor of an empty singly linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SinglyLinkedList( ) {
        head = null;
        tail = null;
        currentSize = 0;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return size()==0;
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
        return new SinglyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * Time complexity is O(1)
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getFirst() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * Time complexity is O(1)
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getLast() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        return tail.getElement();
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * Time complexity is O(n)
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    @Override
    public E get(int position) {
        if ( position < 0 || position >= size() )
            throw new InvalidPositionException();
        if (position == 0)
            return getFirst();
        if (position == size()-1)
            return getLast();
        return getNode(position).getElement();
    }

    /**
     * search for a node in a given position
     * Time complexity is O(1) for best case when position is 0 or the last,
     * overall time complexity is O(n), where n is the number of nodes in the list
     * @param position node's position
     * @return target node
     */
    protected SinglyListNode<E> getNode(int position) {
        SinglyListNode<E> node = head;
        if ( position == size() - 1) {
            return tail;
        }
        for ( int i = 0; i < position; i++)
            node = node.getNext();
        return node;
    }


    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * Time complexity is O(1) for best case when element is the head or tail,
     * overall time complexity is O(n).
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    @Override
    public int indexOf(E element) {
        SinglyListNode<E> node = head;
        if ( tail.getElement().equals(element) ) {
            return size() - 1;
        }
        int position = 0;
        while ( node != null && !node.getElement().equals(element) ) {
            node = node.getNext();
            position++;
        }
        if(node==null)
            return -1;
        return position;
    }

    /**
     * Inserts the specified element at the first position in the list.
     * time complexity is O(1) overall
     * @param element to be inserted
     */
    @Override
    public void addFirst(E element) {
        SinglyListNode<E> newNode = new SinglyListNode<>(element);
        if(isEmpty()) tail = newNode;
        newNode.setNext(head);
        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     * time complexity is O(1) overall
     * @param element to be inserted
     */
    @Override
    public void addLast(E element) {
        if(isEmpty()){ addFirst(element);return;}
        SinglyListNode<E> newNode = new SinglyListNode<>(element, null);
        tail.setNext(newNode);
        tail = newNode;
        currentSize++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * time complexity is O(1) for best case, when inserting the first or last element
     * overall time complexity is O(n)
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public void add(int position, E element) {
        if ( position < 0 || position > size() )
            throw new InvalidPositionException();
        if(position==0)
            this.addFirst(element);
        else if(position==size())
            this.addLast(element);
        else
            this.addMiddle(position,element);

    }
    private void addMiddle(int position, E element) {
        SinglyListNode<E> prevNode = this.getNode(position-1);
        SinglyListNode<E> nextNode = prevNode.getNext();
        SinglyListNode<E> newNode = new SinglyListNode<>(element, nextNode);
        prevNode.setNext(newNode);
        currentSize++;
    }


    /**
     * Removes and returns the element at the first position in the list.
     * time complexity is O(1) overall
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E removeFirst() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        E element = head.getElement();
        head = head.getNext();
        if(head==null)tail=null;
        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * time complexity is O(n) overall
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */

    public E removeLast() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        if ( size() == 1 )return removeFirst();
        E element = tail.getElement();
        tail = getNode(size()-2);
        tail.setNext(null);
        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * Best case is when the element to remove is head, in O(1),
     * time complexity is O(n) overall
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    @Override
    public E remove(int position) {
        if ( position < 0 || position >= size() )
            throw new InvalidPositionException();
        if ( position == 0 )
            return this.removeFirst();
        if ( position == size() - 1 )
            return this.removeLast();
        return this.removeMiddle(position);
    }

    // O(1)
    E removeAfter(SinglyListNode<E> prevNode) {
        SinglyListNode<E> node = prevNode.getNext();
        prevNode.setNext(node.getNext());
        currentSize--;
        if ( node.getNext() == null ) {
            assert node == tail;
            tail = prevNode;
        }
        node.setNext(null);
        return node.getElement();
    }

    private E removeMiddle(int position){
        SinglyListNode<E> prevNode = this.getNode(position-1);
        return removeAfter(prevNode);
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeInt(this.size());
        Iterator<E> iter = this.iterator();
        while(iter.hasNext()) {
            E cur = iter.next();
            oos.writeObject(cur);
        }
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        int size = ois.readInt();
        for(int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E)ois.readObject();
            this.addLast(element);
        }
    }
}
