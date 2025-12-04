package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * Map with a singly linked list with head and size
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
class MapSinglyList<K,V> implements Map<K, V> {


    transient private SinglyListNode<Entry<K,V>> head;

    transient private int size;

    public MapSinglyList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
  
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Iterator<Entry<K,V>> iter = this.iterator();
        while(iter.hasNext()) {
            Entry<K,V> cur = iter.next();
            if(cur.key().equals(key)) {
                return cur.value();
            }
        }
        return null;
    }
    
    private SinglyListNode<Entry<K,V>> getNode(K key) {
        if(this.isEmpty()) { return null; }
        SinglyListNode<Entry<K,V>> cur = this.head;
        while (cur.getNext() != null && !cur.getElement().key().equals(key)) {
            cur = cur.getNext();
        }
        return cur.getElement().key().equals(key) ? cur : null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    
    public V put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value);
        SinglyListNode<Entry<K,V>> node = this.getNode(key);
        if(node == null) {
            size++;
            this.head = new SinglyListNode<>(entry, this.head);
            return null;
        } else {
            V res = node.getElement().value();
            node.setElement(entry);
            return res;
        }
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        if(this.isEmpty()) { return null; }
        if(this.head.getElement().key().equals(key)) {
            V res = this.head.getElement().value();
            this.head.setElement(null);
            this.head = this.head.getNext();
            this.size--;
            return res;
        }
        SinglyListNode<Entry<K,V>> cur = this.head;
        while(cur.getNext() != null && !cur.getNext().getElement().key().equals(key)) {
            cur = cur.getNext();
        }
        boolean found = cur.getNext() != null && cur.getNext().getElement().key().equals(key);
        if (found) {
            V res = cur.getNext().getElement().value();
            cur.getNext().setElement(null);
            cur.setNext(cur.getNext().getNext());
            this.size--;
            return res;
        } else {
            return null;
        }
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
    
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeInt(this.size);
        Iterator<Map.Entry<K,V>> iter = this.iterator();
        while(iter.hasNext()) {
            Map.Entry<K,V> cur = iter.next();
            oos.writeObject(cur);
        }
    }
    
    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        int size = ois.readInt();
        SinglyListNode<Map.Entry<K,V>> cur = null;
        for(int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            Map.Entry<K,V> element = (Map.Entry<K,V>)ois.readObject();
            if ( cur == null ) {
                this.head = new SinglyListNode<>(element);
                cur = this.head;
            } else {
                cur.setNext(new SinglyListNode<>(element));
                cur = cur.getNext();
            }
        }
        this.size = size;
    }
}
