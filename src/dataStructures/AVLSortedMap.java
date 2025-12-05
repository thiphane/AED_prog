package dataStructures;

/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     * "rebalance" from the given node to the root, calling restructure when a subtree is unbalanced
     * if "rebalance" is called after an add operation, it stops after the first restructure
     * time complexity in worst case is O(h) - h is the height of the tree -
     * when the node to be restructured is the root, leading to O(log n).
     * @param cur node where to start
     * @param remove true if rebalance operation is called after a remove operation
     */
    private void rebalance(AVLNode<Entry<K,V>> cur, boolean remove) {
        AVLNode<Entry<K,V>> parent = cur;
        while ( parent != null ) {
            if ( parent.isUnbalanced() ) {
                AVLNode<Entry<K,V>> newRoot = (AVLNode<Entry<K,V>>) restructure(getHighestChild(getHighestChild(parent)));
                if ( newRoot.getParent() == null ) { this.root = newRoot; }
                if ( !remove ) {
                    break;
                }
            }
            parent = (AVLNode<Entry<K,V>>) parent.getParent();
        }
        assert this.debug_isBalanced();
    }

    /**
     * Add a new node when its parent is a leaf
     * Time complexity O(1)
     * @param cur new node's parent
     * @param key new node key
     * @param value new node value
     * @param left if new node is left child
     * @pre cur is a leaf node
     */
    private void addLeafNode(AVLNode<Entry<K,V>> cur, K key, V value, boolean left) {
        int oldHeight = cur.getHeight();
        AVLNode<Entry<K,V>> newNode = new AVLNode<>(new Entry<>(key, value), cur);
        if ( left ) {
            cur.setLeftChild(newNode);
        } else {
            cur.setRightChild(newNode);
        }
        if ( cur.getHeight() != oldHeight ) {
            rebalance(cur, false);
        }
        this.currentSize++;
    }

    /**
     * Inserts a new key-value pair into the tree, or update an existing entry, if key already exists
     * time complexity is O(h), where h is the height of the tree for expected case
     * h will be around log n, leading to O(log n)
     * @param key key to the new entry
     * @param value value to the new entry
     * @return old value if key already exists of return null if it's a new entry
     */
    public V put(K key, V value) {
        if ( root == null ) {
            root = new AVLNode<>(new Entry<>(key, value));
            this.currentSize++;
            return null;
        }
        V res;
        AVLNode<Entry<K,V>> cur = (AVLNode<Entry<K,V>>) root;
        while (!isKey(cur, key)) {
            Entry<K,V> elm = cur.getElement();
            int comparison = key.compareTo(elm.key());
            boolean left;
            AVLNode<Entry<K,V>> newEl;
            if ( comparison <= 0 ) {
                newEl = (AVLNode<Entry<K,V>>)cur.getLeftChild();
                left = true;
            } else {
                newEl = (AVLNode<Entry<K,V>>)cur.getRightChild();
                left = false;
            }
            if ( newEl == null ) {
                addLeafNode(cur, key, value, left);
                return null;
            }
            cur = newEl;
        }
        res = cur.getElement().value();
        cur.setElement(new Entry<>(key, value));
        return res;
    }

    /**
     * Gets the child with the biggest height,
     * or the left one if both are the same height
     * O(1)
     * @param y the parent
     * @return the child with the biggest height
     * @pre y != null && (y.getRightChild() != null || y.getLeftChild() != null)
     */
    private AVLNode<Entry<K, V>> getHighestChild(AVLNode<Entry<K,V>> y) {
        AVLNode<Entry<K,V>> yr = (AVLNode<Entry<K,V>>) y.getRightChild();
        int rh = yr == null ? -1 : yr.getHeight();
        AVLNode<Entry<K,V>> yl = (AVLNode<Entry<K,V>>) y.getLeftChild();
        int lh = yl == null ? -1 : yl.getHeight();
        if ( lh > rh) {
            return yl;
        } else {
            return yr;
        }
    }

    /**
     * Remove a given key from the tree,
     * time complexity of expected case if O(h), where h is the tree height,
     * and the tree's height will be around log n, leading to O(log n)
     * @param key whose entry is to be removed from the map
     * @return value of the removed entry  or null if entry does not exist
     */
    public V remove(K key) {
        BTNode<Entry<K, V>> node = getNode((BTNode<Entry<K,V>>) root, key);
        if ( node == null ) { return null; }
        BTNode<Entry<K,V>> parent = ((BTNode<Entry<K,V>>) node.getParent());
        V val = node.getElement().value();
        int children = 0;
        if ( node.getLeftChild() != null ) { children++; }
        if ( node.getRightChild() != null ) { children++; }
        if ( children == 0 ) {
            assert node.isLeaf();
            node.setElement(null);
            if ( parent == null ) {
                this.root = null;
            } else {
                if ( parent.getLeftChild() == node ) {
                    parent.setLeftChild(null);
                } else {
                    assert node == parent.getRightChild();
                    parent.setRightChild(null);
                }
                rebalance((AVLNode<Entry<K,V>>) parent, true);
            }
        }
        else if ( children == 1 ) {
            BTNode<Entry<K,V>> replacingWith;
            if ( node.getLeftChild() != null) {
                replacingWith = (BTNode<Entry<K,V>>) node.getLeftChild();
            } else {
                assert node.getLeftChild() == null && node.getRightChild() != null;
                replacingWith = (BTNode<Entry<K,V>>) node.getRightChild();
            }
            if ( parent == null ) {
                root = replacingWith;
            }
            else if ( node.getElement().key().compareTo(parent.getElement().key()) < 0) {
                parent.setLeftChild(replacingWith);
            } else {
                parent.setRightChild(replacingWith);
            }
            node.setElement(null);
            if ( parent != null ) {
                rebalance((AVLNode<Entry<K,V>>) parent, true);
            }
            replacingWith.setParent(parent);
        }
        else {
            BTNode<Entry<K,V>> replacingWith = ((BTNode<Entry<K,V>>) node.getLeftChild()).furtherRightElement();
            node.setElement(replacingWith.getElement());
            replacingWith.setElement(null);
            BTNode<Entry<K, V>> replaceParent = replaceWithLeft(replacingWith);
            replacingWith.setParent(null);
            rebalance((AVLNode<Entry<K,V>>) replaceParent, true);
        }
        this.currentSize--;
        return val;
    }

    private BTNode<Entry<K, V>> replaceWithLeft(BTNode<Entry<K, V>> replacingWith) {
        BTNode<Entry<K,V>> replaceParent = (BTNode<Entry<K,V>>) replacingWith.getParent();
        BTNode<Entry<K,V>> newChild = (BTNode<Entry<K,V>>) replacingWith.getLeftChild();
        if ( replaceParent.getLeftChild() == replacingWith) {
            replaceParent.setLeftChild(newChild);
        } else {
            assert replaceParent.getRightChild() == replacingWith;
            replaceParent.setRightChild(newChild);
        }
        if ( newChild != null) {
            newChild.setParent(replaceParent);
        }
        return replaceParent;
    }

    public boolean debug_isBalanced() {
        // only use in assertions, since i believe they only run in debug mode
        return isBalancedRec((AVLNode<Entry<K,V>>) root);
    }

    private boolean isBalancedRec(AVLNode<Entry<K,V>> cur) {
        if ( cur == null ) { return true; }
        if ( cur.isUnbalanced() ) {
            return false;
        }
        return isBalancedRec((AVLNode<Entry<K,V>>) cur.getLeftChild()) &&
                isBalancedRec((AVLNode<Entry<K,V>>) cur.getRightChild());
    }
}
