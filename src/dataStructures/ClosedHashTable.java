package dataStructures;
/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    /**
     * O(1)
     * @param capacity the initial capacity for the table
     */
    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        table = (Entry<K,V>[]) new Entry[arraySize];
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    // O(1)
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }

    /**
     * O(1) best and expected case if no collisions happened,
     * O(n) worst case if a lot of collisions
     * happened and most elements are concentrated
     * at the hash's index
     * @param key the key to search for
     * @param returnDeleted whether deleted entries should be returned
     * @return the index of the key, or to place the key
     */
    int linearGetTarget(K key, boolean returnDeleted) {
        for(int i = 0; i < table.length; i++) {
            int h = hash(key, i);
            if ( table[h] == null ) { return h; }
            if ( table[h] != null && key.equals(table[h].key()) ) {
                return h;
            }
            if ( returnDeleted && table[h] != null && table[h] == REMOVED_CELL ) {
                return h;
            }
        }
        return NOT_FOUND;
    }
    /**
     * Linear Proving
     *
     * Same time complexity as linearGetTarget
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        //TODO: Left as an exercise.
        int i = linearGetTarget(key, false);
        if ( i == -1 ) { return NOT_FOUND; }
        if ( table[i] == null ) { return NOT_FOUND; }
        return key.equals(table[i].key()) ? i : NOT_FOUND;
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * Same time complexity as linearGetTarget
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        //TODO: Left as an exercise.
        int p = searchLinearProving(key);
        if ( p == NOT_FOUND ) {
            return null;
        }
        return table[p].value();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Best case has same time complexity as linearGetTarget(),
     * worst case if rehashing is needed, going to O(n)
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        int i = linearGetTarget(key, true);
        if ( i == -1 ) { throw new RuntimeException("closed put"); }
        V old = table[i] == null || table[i] == REMOVED_CELL ? null : table[i].value();
        table[i] = new Entry<>(key, value);
        currentSize++;
        return old;
    }

    /**
     * Time complexity O(n)
     */
    @SuppressWarnings("unchecked")
     private void rehash(){
        //TODO: Left as an exercise.
         int arrSize = HashTable.nextPrime(this.table.length * 2);
         Entry<K,V>[] oldTable = table;
         this.table = (Entry<K,V>[])new Entry[arrSize];
         this.currentSize = 0;
         for (Entry<K, V> cur : oldTable) {
            if (cur != REMOVED_CELL && cur != null) {
                this.put(cur.key(), cur.value());
            }
        }
     }

   
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Same time complexity as linearGetTarget
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        //TODO: Left as an exercise.
        int i = searchLinearProving(key);
        if ( i == NOT_FOUND ) {
            return null;
        }
        V old = table[i].value();
        table[i] = (Entry<K,V>)REMOVED_CELL;
        currentSize--;
        return old;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(new ArrayIterator<>(this.table, this.table.length), (f) -> f != null && f != REMOVED_CELL);
    }

}
