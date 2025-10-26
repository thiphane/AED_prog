package dataStructures;
/**
 * Array Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {
    private E[] elems;
    private int counter;
    private int current;
    
    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }
    
    @Override
    public void rewind() {
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        return this.current < this.counter;
    }

    @Override
    public E next() {
        return this.elems[this.current++];
    }

}
