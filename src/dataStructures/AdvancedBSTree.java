package dataStructures;
/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{

    /**
     * Performs a single left rotation rooted at z node.
     * Node y was a  right  child  of z before the  rotation,
     * then z becomes the left child of y after the rotation.
     * Time complexity is O(1) since all the operations are primitive
     * @param z - root of the rotation
     * @pre: z has a right child
     */
    protected void rotateLeft( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) z.getRightChild();
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) z.getParent();
        BTNode<Entry<K,V>> child = ((BTNode<Entry<K,V>>)y.getLeftChild());
        z.setRightChild(child);
        if ( child != null ) {
            child.setParent(z);
        }
        y.setLeftChild(z);
        y.setParent(parent);
        z.setParent(y);
        if ( parent != null ) {
            if ( parent.getLeftChild() == z ) {
                parent.setLeftChild(y);
            } else {
                assert parent.getRightChild() == z;
                parent.setRightChild(y);
            }
        }
	}

     /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
      * the complexity is O(1) since all the operations are primitive
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>)z.getLeftChild();
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) z.getParent();
        BTNode<Entry<K,V>> child = ((BTNode<Entry<K,V>>)y.getRightChild());
        z.setLeftChild(child);
        if ( child != null ) {
            ((BTNode<Entry<K,V>>)y.getRightChild()).setParent(z);
        }
        y.setRightChild(z);
        y.setParent(z.getParent());
        z.setParent(y);
        if ( parent != null ) {
            if ( parent.getLeftChild() == z ) {
                parent.setLeftChild(y);
            } else {
                assert parent.getRightChild() == z;
                parent.setRightChild(y);
            }
        }
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     * time complexity is O(1) since all the operations are primitive
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        BTNode<Entry<K,V>> y = (BTNode<Entry<K,V>>) x.getParent();
        BTNode<Entry<K,V>> z = (BTNode<Entry<K,V>>) y.getParent();
        if ( z.getLeftChild() == y ) {
            if ( y.getLeftChild() == x ) {
                // Caso 1: linha para a esquerda: x > y > z
                this.rotateRight(z);
                return y;
            } else {
                assert y.getRightChild() == x;
                // Caso 2: z > x > y
                this.rotateLeft(y);
                this.rotateRight(z);
                return x;
            }
        } else {
            assert z.getRightChild() == y;
            if ( y.getLeftChild() == x ) {
                // Caso 3: y > x > z
                this.rotateRight(y);
                this.rotateLeft(z);
                return x;
            } else {
                // Caso 4: linha para a direita: z > y > x
                assert y.getRightChild() == x;
                this.rotateLeft(z);
                return y;
            }
        }
    }
}
