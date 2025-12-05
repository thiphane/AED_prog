package dataStructures;

public class ObjectRemovalSinglyList<E> extends SinglyLinkedList<E> implements ObjectRemovalList<E> {
    /**
     * Custom method to remove a given object
     * SinglyLinkedList remove method don't allow direct removal, this custom class allow direct removal
     *
     * @param object to be removed
     * @return removed element
     */
    @Override
    public E remove(E object) {
        SinglyListNode<E> cur = this.getNode(0); // head is private, not protected, and it's from the given dataStructures package so no changing it
        if ( cur.getElement().equals(object) ) {
            return this.removeFirst();
        }
        while ( cur.getNext() != null && !cur.getNext().getElement().equals(object) ) {
            cur = cur.getNext();
        }
        if ( cur.getNext() == null ) {
            return null;
        }
        return this.removeAfter(cur);
    }

}
