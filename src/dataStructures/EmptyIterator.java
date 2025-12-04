package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

public class EmptyIterator<E> implements Iterator<E> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        throw new NoSuchElementException();
    }

    @Override
    public void rewind() {}
}
