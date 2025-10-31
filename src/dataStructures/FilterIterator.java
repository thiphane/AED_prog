package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 *
 * @param <E> Generic Element
 * @author AED  Team
 * @version 1.0
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     * Iterator of elements to filter.
     */
    Iterator<E> iterator;

    /**
     * Filter.
     */
    Predicate<E> criterion;

    /**
     * Node with the next element in the iteration.
     */
    E nextToReturn;


    /**
     * Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     *
     * @param list      to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        this.iterator = list;
        this.criterion = criterion;
        updateNext();
    }

    /**
     * Time complexity: Best case: O(1), Base Case O(n), Worst case: O(n).
     */
    private void updateNext() {
        if (this.iterator.hasNext()) {
            this.nextToReturn = this.iterator.next();
        } else {
            this.nextToReturn = null;
            return;
        }
        while (this.iterator.hasNext() && !this.criterion.check(this.nextToReturn)) {
            this.nextToReturn = this.iterator.next();
        }
        if (!this.criterion.check(this.nextToReturn)) {
            this.nextToReturn = null;
        }
    }

    /**
     * Returns true if next would return an element
     *Time complexity: Best case: O(1), Base Case O(1), Worst case: O(1).
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return this.nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *Time complexity: Best case: O(1), Base Case O(n), Worst case: O(n).
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        E element = this.nextToReturn;
        updateNext();
        return element;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * Time complexity: Best case: O(1), Base Case O(n), Worst case: O(n).
     */
    public void rewind() {
        this.iterator.rewind();
        this.nextToReturn = null;
        updateNext();
    }

}
