package dataStructures;

public class ObjectRemovalSinglyList<E> extends SinglyLinkedList<E> implements ObjectRemovalList<E> {
    @Override
    public E remove(E object) {
        SinglyListNode<E> cur = this.getNode(0);
        if ( cur.getElement().equals(object) ) {
            return this.removeFirst();
        }
        while ( cur.getNext() != null && !cur.getNext().getElement().equals(object) ) {
            cur = cur.getNext();
        }
        if ( cur.getNext() == null ) {
            return null;
        }
        assert cur.getNext().getElement().equals(object);
        return this.removeAfter(cur);
    }


}
