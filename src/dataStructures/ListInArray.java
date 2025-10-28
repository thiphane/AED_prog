package dataStructures;
import dataStructures.exceptions.*;
/**
 * List in Array
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class ListInArray<E> implements List<E> {

    private static final int FACTOR = 2;
    /**
     * Array of generic elements E.
     */
    private E[] elems;

    /**
     * Number of elements in array.
     */
    private int counter;


    /**
     * Construtor with capacity.
     * @param dimension - initial capacity of array.
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    /**
     * Returns true iff the list contains no elements.
     *
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return counter==0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     *
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return this.elems[0];
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return this.elems[this.size() - 1];
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get(int position) {
        if(position < 0 || position >= this.size()) {
            throw new InvalidPositionException();
        }
        return this.elems[position];
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf(E element) {
        for(int i = 0; i < this.size(); i++) {
            if(this.elems[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }


    @SuppressWarnings("unchecked")
    private void checkAddSize() {
        if(this.elems.length <= this.size()) {
            E[] newArr = (E[])(new Object[this.elems.length * FACTOR]);
            for(int i = 0; i < this.size(); i++) {
                newArr[i] = this.elems[i];
            }
            this.elems = newArr;
        }
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    public void addFirst(E element) {
        this.checkAddSize();
        for(int i = this.size(); i > 0; i--) {
            this.elems[i] = this.elems[i-1];
        }
        this.elems[0] = element;
        this.counter++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    public void addLast(E element) {
        this.checkAddSize();
        this.elems[this.counter++] = element;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add(int position, E element) {
        this.checkAddSize();
        if (position < 0 || position > this.size()) {
            throw new InvalidPositionException();
        }
        for(int i = this.size(); i > position; i--) {
            this.elems[i] = this.elems[i-1];
        }
        this.elems[position] = element;
        this.counter++;
    }

    /**
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }
        E elem = this.elems[0];
        this.counter--;
        for(int i = 0; i < this.size(); i++) {
            this.elems[i] = this.elems[i+1];
        }
        this.elems[counter] = null;
        return elem;
    }

    /**
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }
        E elem = this.elems[this.size() - 1];
        this.elems[this.size() - 1] = null;
        this.counter--;
        return elem;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove(int position) {
        if (position < 0 || position >= this.size()) {
            throw new InvalidPositionException();
        }
        E elem = this.elems[position];
        for(int i = position; i < size()-1; i++) {
            this.elems[i] = this.elems[i+1];
        }
        this.elems[size() - 1] = null;
        this.counter--;
        return elem;
    }
}
