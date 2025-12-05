package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {
    Map<K,V>[] table;
    int counter;
    Iterator<Map.Entry<K, V>> inner;

    // Same time complexity as advance
    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        this.counter = 0;
        advance();
        if (this.counter < this.table.length && this.table[this.counter] != null) {
            this.inner = this.table[this.counter].iterator();
        } else { this.inner = null; }
    }

    /**
     * Best case O(1) if the current collision table has elements,
     * worst case O(n) if no more collision tables have elements
     */
    private void advance() {
        while (this.counter < this.table.length && this.table[this.counter] == null) {
            this.counter++;
        }
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * O(1)
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return inner != null && inner.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * Same complexity as advance
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if(!this.hasNext()) { throw new NoSuchElementException(); }
        Map.Entry<K,V> res = inner.next();
        if (!inner.hasNext()) {
            this.counter++;
            advance();
            if (this.counter < this.table.length && this.table[this.counter] != null) {
                inner = this.table[this.counter].iterator();
            } else { inner = null; }
        }
        return res;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * Same time complexity as advance
     */
    public void rewind() {
        this.counter = 0;
        this.inner = null;
        this.advance();
    }
}

