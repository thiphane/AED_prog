package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

public class HashMapList<V> implements TwoWayList<V>, ObjectRemovalList<V>, ContainCheckingList<V> {

    transient protected DoublyListNode<V> head;
    transient protected DoublyListNode<V> tail;
    transient private Map<V,DoublyListNode<V>> map;
    transient protected int currentSize;

    transient private boolean hasSerialized; // This is necessary because objects returned by ois.readObject aren't fully initialized,
    transient private Queue<V> serializedToAdd; // so since getNode needs to call .equals that results in an exception if accessed before

    public HashMapList(int capacity) {
        map = new SepChainHashTable<>(capacity); // this is only used for the services, which are never removed
        head = null;
        tail = null;
        currentSize = 0;
        hasSerialized = false;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
    @Override
    public boolean isEmpty() {
        checkSerialization();
        return head==null;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        checkSerialization();
        return currentSize;
    }

    @Override
    public Iterator<V> iterator() {
        checkSerialization();
        return new DoublyIterator<>(this.head);
    }

    @Override
    public TwoWayIterator<V> twoWayiterator() {
        checkSerialization();
        return new TwoWayDoublyIterator<>(this.head, this.tail);
    }

    @Override
    public V getFirst() {
        checkSerialization();
        return this.head.getElement();
    }

    @Override
    public V getLast() {
        checkSerialization();
        return this.tail.getElement();
    }

    @Override
    public V get(int position) {
        checkSerialization();
        if ( position < 0 || position >= size()) {
            throw new NoSuchElementException();
        }
        if ( position == size() - 1 ) {
            return this.tail.getElement();
        }
        DoublyListNode<V> cur = head;
        for(int i = 0; i < position; i++) {
            cur = cur.getNext();
        }
        return cur.getElement();
    }

    protected DoublyListNode<V> getNode(int position) {
        checkSerialization();
        if(position >= size() || position < 0) {
            throw new InvalidPositionException();
        }
        boolean backwards = position > (this.size() / 2);
        DoublyListNode<V> cur;
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

    @Override
    public int indexOf(V element) {
        checkSerialization();
        DoublyListNode<V> cur = head;
        int idx = 0;
        while(cur != null && !cur.getElement().equals(element)) {
            cur = cur.getNext();
            idx++;
        }
        if ( cur == null ) { return -1; }
        return cur.getElement().equals(element) ? idx : -1;
    }

    @Override
    public boolean contains(V object) {
        return this.map.get(object) != null;
    }

    @Override
    public void addFirst(V element) {
        checkSerialization();
        if(this.head == null) {
            this.head = new DoublyListNode<>(element);
            this.tail = this.head;
        } else {
            this.head.setPrevious(new DoublyListNode<>(element, null, this.head));
            this.head = this.head.getPrevious();
        }
        if ( map.get(element) != null ) {
            throw new RuntimeException("repet element in hashmaplist");
        }
        map.put(element, this.head);
        currentSize++;
    }

    @Override
    public void addLast(V element) {
        checkSerialization();
        if(this.tail == null) {
            this.tail = new DoublyListNode<>(element);
            this.head = this.tail;
        } else {
            this.tail.setNext(new DoublyListNode<>(element, this.tail, null));
            this.tail = this.tail.getNext();
        }
        this.map.put(element, this.tail);
        currentSize++;
    }

    @Override
    public void add(int position, V element) {
        checkSerialization();
        if(position == 0) { this.addFirst(element); return; }
        if(position == size()) { this.addLast(element); return; }
        // Add to middle
        DoublyListNode<V> newNext = this.getNode(position);
        DoublyListNode<V> node = new DoublyListNode<>(element, newNext.getPrevious(), newNext);
        newNext.setPrevious(node);
        node.getPrevious().setNext(node);
        this.map.put(element, node);
        currentSize++;
    }

    @Override
    public V removeFirst() {
        checkSerialization();
        if(size() <= 0) { throw new NoSuchElementException(); }
        V element = this.head.getElement();
        this.head = this.head.getNext();
        if(this.head == null) { this.tail = null; } else { this.head.setPrevious(null); }
        currentSize--;
        this.map.remove(element);
        return element;
    }

    @Override
    public V removeLast() {
        checkSerialization();
        if(size() <= 0) { throw new NoSuchElementException(); }
        V element = this.tail.getElement();
        this.tail = this.tail.getPrevious();
        if(this.tail == null) { this.head = null; } else { this.tail.setNext(null); }
        currentSize--;
        this.map.remove(element);
        return element;
    }

    private V removeMiddle(DoublyListNode<V> toRemove) {
        checkSerialization();
        // Remove middle
        toRemove.getPrevious().setNext(toRemove.getNext());
        toRemove.getNext().setPrevious(toRemove.getPrevious());
        currentSize--;
        return toRemove.getElement();
    }


    @Override
    public V remove(int position) {
        checkSerialization();
        if(position < 0 || position > size()) {
            throw new InvalidPositionException();
        }
        if(position == 0) { return removeFirst(); }
        if(position == size()-1) { return removeLast(); }
        return removeMiddle(getNode(position));
    }

    // O(1)
    @Override
    public V remove(V object) {
        checkSerialization();
        DoublyListNode<V> node = this.map.remove(object);
        if (node != null) {
            if ( node.getPrevious() == null ) {
                return removeFirst();
            } else if ( node.getNext() == null ) {
                return removeLast();
            }
            return removeMiddle(node);
        }
        return null;
    }

    private void checkSerialization() {
        // https://stackoverflow.com/questions/3298915/deserialized-object-has-null-in-all-fields
        if ( hasSerialized ) {
            hasSerialized = false;
            while ( !serializedToAdd.isEmpty() ) {
                this.addLast(serializedToAdd.dequeue());
            }
            serializedToAdd = null;
        }
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(this.currentSize);
        Iterator<V> iter = this.iterator();
        while(iter.hasNext()) {
           V cur = iter.next();
            oos.writeObject(cur);
        }
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        serializedToAdd = new QueueInList<>();
        int size = ois.readInt();
        this.map = new SepChainHashTable<>(size);
        for(int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            V element = (V)ois.readObject();
            serializedToAdd.enqueue(element);
        }
        hasSerialized = true;
    }
}
