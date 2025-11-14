package campus_app.app;

import dataStructures.Iterator;
import dataStructures.List;

public class ArrayOfListIterator<E> implements Iterator<E> {
    List<E>[] elements;
    int counter;
    Iterator<E> cur;
    E next;
    public ArrayOfListIterator(List<E>[] elements) {
        this.elements = elements;
        this.rewind();
    }

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

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public E next() {
        E res = this.next;
        advance();
        return res;
    }

    @Override
    public void rewind() {
        this.counter = 0;
        this.cur = elements[counter++].iterator();
        this.advance();
    }
}
