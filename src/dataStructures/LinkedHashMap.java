package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * A map which stores the order its entries were inserted in
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public class LinkedHashMap<K,V> implements Map<K,V>{

    transient protected DoublyListNode<Entry<K,V>> head;
    transient protected DoublyListNode<Entry<K,V>> tail;
    transient private Map<K,DoublyListNode<Entry<K,V>>> map;


    public LinkedHashMap(int capacity) {
        map = new ClosedHashTable<>(capacity); // this is only used for the services, which are never removed
        head = null;
        tail = null;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
    @Override
    public boolean isEmpty() {
        return head==null;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * Time complexity is O(1)
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        DoublyListNode<Entry<K,V>> node =  map.get(key);
        if(node==null)return null;
        return node.getElement().value();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Time complexity for expected case is O(1), in worst case is O(n),
     * for when then table needs to be rehashed
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        DoublyListNode<Entry<K,V>> valueToReturn = map.get(key); //O(1)
        Entry<K,V> entry = new Entry<K,V> (key, value);
        if(valueToReturn==null){
            //O(1)
            DoublyListNode<Entry<K,V>> newNode;
            if(head==null) {
                newNode = new DoublyListNode<>(entry);
                head = newNode;
            }
            else{
                newNode = new DoublyListNode<>(entry, tail, null);
                tail.setNext(newNode);
            }
            tail = newNode;
            map.put(key, newNode);//worst case O(n)
            return null;
        }
        else valueToReturn.setElement(entry);
        return valueToReturn.getElement().value();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Time complexity for expected case is O(1), in worst case is O(n),
     * for when then table needs to be rehashed
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        DoublyListNode<Entry<K,V>> nodeToRemove = map.remove(key);
        if(nodeToRemove!=null){
            if(nodeToRemove==head){
                head = head.getNext();
                if(head!=null){
                    head.setPrevious(null);
                }else tail = null;
            }else if(nodeToRemove==tail){
                nodeToRemove.getPrevious().setNext(null);
                tail = nodeToRemove.getPrevious();
            } else {
                nodeToRemove.getPrevious().setNext(nodeToRemove.getNext());
                nodeToRemove.getNext().setPrevious(nodeToRemove.getPrevious());
            }
            return nodeToRemove.getElement().value();
        }return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K,V>> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
    public Iterator<V> values() {
        return new TransformerIterator<Entry<K,V>, V>(iterator(), Entry::value);
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
    public Iterator<K> keys() {
        return new TransformerIterator<Entry<K,V>, K>(iterator(), Entry::key);
    }
    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeInt(this.size());
        Iterator<Entry<K,V>> iter = iterator();
        while(iter.hasNext()) {
            Entry<K,V> cur = iter.next();
            oos.writeObject(cur);
        }
    }

    @Serial
    private void readObject(ObjectInputStream ois)throws IOException, ClassNotFoundException {
        int size = ois.readInt();
        this.map = new ClosedHashTable<>(size);
        for(int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            Entry<K,V> element = (Entry<K,V>)ois.readObject();
            this.put(element.key(),element.value());
        }
    }
}
