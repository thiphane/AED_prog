package campus_app.app;

import dataStructures.Iterator;
import dataStructures.List;

public class ArrayOfListIterator<E> implements Iterator<E> {
    List<E>[] elements;
    int counter;
    Iterator<E> cur;
    E next;

    /**
     * O(1)
     */
    public ArrayOfListIterator(List<E>[] elements) {
        this.elements = elements;
        this.rewind();
    }

    /**
     * Find the next array which has items
     *
     * Best case time complexity is O(1) when the next array has items,
     * worst case is O(n) when no other array has items
     */
    private void advance() {
        while(!cur.hasNext() && counter < elements.length) {
            cur = elements[counter++].iterator();
        }
        if ( cur.hasNext() ) {
            this.next = cur.next();
        } else {
            this.next = null;
        }
    }

    /**
     * O(1)
     * @return if there are more elements to iterate through
     */
    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    /**
     * Return the next element in the iteration
     *
     * Same time complexity as advance
     */
    @Override
    public E next() {
        E res = this.next;
        advance();
        return res;
    }

    /**
     * Restart the iteration
     *
     * Same time complexity as advance
     */
    @Override
    public void rewind() {
        this.counter = 0;
        this.cur = elements[counter++].iterator();
        this.advance();
    }
}
