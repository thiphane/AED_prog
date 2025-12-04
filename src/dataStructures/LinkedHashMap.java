package dataStructures;

public class LinkedHashMap<K,V> extends SepChainHashTable<K,V>{
    protected final List<Entry<K,V>> insertionOrder;
    public LinkedHashMap(int capacity) {
        super(capacity);
        insertionOrder = new SinglyLinkedList<>();

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
    @Override
    public V put(K key, V value) {
        V valueToReturn = super.put(key, value);
        if(valueToReturn==null) {
            insertionOrder.addLast(new Entry<>(key, value));
        } return valueToReturn;
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
    @Override
    public V remove(K key) {
        V valueToReturn = super.remove(key);
        if(valueToReturn!=null) {
            Entry<K,V> entry = new Entry<>(key, valueToReturn);
            ((ObjectRemovalList<Entry<K,V>>)insertionOrder).remove(entry);
        }
        return valueToReturn;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K,V>> iterator() {
        return insertionOrder.iterator();
    }
}
