package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;

    /**
     * Root Node
     */
    private BTNode<E> root;

    /**
     * Same time complexity as rewind
     * @param root the root node
     */
    public  InOrderIterator(BTNode<E> root) {
        this.root=root;
        rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * O(1)
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return next!=null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * Same time complexity as advance, O(log n)
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem=next.getElement();
        advance(); // O(log n)
        return elem;
    }

    /**
     * Expected time complexity O(log n), best case O(1) if the node we are on
     * is the left child of its parent
     */
    private void advance() {
        // next is never null, since then next() would throw an exception and not call advance()
        if ( this.next.getRightChild() != null ) {
            // if we just printed a node which also has a right node, we can assume we came from the left since
            // we're going in ascending order
            // so, return the furthest left element of the right node (smallest element that is also bigger than the current)
            this.next = ((BTNode<E>) this.next.getRightChild()).furtherLeftElement();
        } else {
            // if it doesn't have a right child, go up until we come from the left or we get to the root and then return the parent
            // if we get to the root from the right, we've finished iterating
            BTNode<E> cur = this.next;
            while ( cur.getParent() != null && cur == ((BTNode<E>) cur.getParent()).getRightChild() ) {
                cur = (BTNode<E>)cur.getParent();
            }
            this.next = (BTNode<E>)cur.getParent();
        }
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     *
     * Expected case O(log n), best case O(1) if the root has no left child,
     * worst case O(n) if the tree and it's descendants only have a left child
     */
    public void rewind() {
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }
}
