package dataStructures;
/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity ){
        super(nextPrime(capacity));
        int primeCapacity = nextPrime(capacity);
        this.table = new MapSinglyList[primeCapacity];
        this.maxSize = Math.round(primeCapacity * MAX_LOAD_FACTOR);
    }

    // Returns the hash value of the specified key.
    protected int hash( K key){
        return Math.abs( key.hashCode() ) % this.table.length;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        Map<K,V> sub = this.table[this.hash(key)];
        if(sub == null) {
            return null;
        }
        return sub.get(key);
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
        if (isFull())
            rehash();
        int hsh = this.hash(key);
        Map<K,V> sub = this.table[hsh];
        if(sub == null) {
            sub = new MapSinglyList<>();
            this.table[hsh] = sub;
        }
        V old = sub.put(key, value);
        if(old == null) {
            currentSize++;
        }
        return old;
    }


    @SuppressWarnings("unchecked")
    private void rehash() {
        Map<K,V>[] oldTable = this.table;
        this.table = new MapSinglyList[HashTable.nextPrime(this.table.length * 2)];
        this.maxSize = Math.round(this.table.length * MAX_LOAD_FACTOR);
       // System.out.printf("rehashing from %d -> %d\n", oldTable.length, this.table.length);
        for(Map<K,V> curTable : oldTable) {
            if(curTable == null) { continue; }
            Iterator<Entry<K,V>> iter = curTable.iterator();
            while(iter.hasNext()) {
                Entry<K,V> cur = iter.next();
                int hash = this.hash(cur.key());
                Map<K,V> chosen = this.table[hash];
                if(chosen == null) {
                    chosen = new MapSinglyList<>();
                    this.table[hash] = chosen;
                }
                chosen.put(cur.key(), cur.value());
            }
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
        int hash = this.hash(key);
        Map<K,V> sub = this.table[hash];
        if(sub == null) {
            sub = new MapSinglyList<>();
            this.table[hash] = sub;
        }
        V old = sub.remove(key);
        if (old != null) {
            this.currentSize--;
        }
        return old;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }
}
