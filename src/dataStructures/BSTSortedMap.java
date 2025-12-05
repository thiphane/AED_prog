package dataStructures;

import dataStructures.exceptions.EmptyMapException;
/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{
    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     * time complexity is O(1) overall
     * @return smallest element on the map
     * @throws EmptyMapException When map is empty
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     * time complexity is O(1) overall
     * @return largest element found on map
     * @throws EmptyMapException When map is empty
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * time complexity for best case is O(1) when the given key is equal to the root
     * otherwise overall time complexity is O(h), where h is the height of the tree.
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    protected boolean isKey(Node<Entry<K,V>> node, K key) {
        return key.equals(node.getElement().key());
    }

    /**
     * Search for a certain node.
     * Time complexity its O(h), where h is the height of the tree
     * @param node starter node to search
     * @param key key from the entry we are looking for
     * @return node or null if key don't belong to any entry
     */
    BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        //TODO: Left as an exercise.
        BTNode<Entry<K,V>> cur = node;
        while (cur != null && !isKey(cur, key)) {
            Entry<K,V> elm = cur.getElement();
            int comparison = key.compareTo(elm.key());
            if ( comparison < 0 ) {
                cur = (BTNode<Entry<K,V>>)cur.getLeftChild();
            } else if ( comparison > 0 ) {
                cur = (BTNode<Entry<K,V>>)cur.getRightChild();
            } else {
                throw new RuntimeException("Not a natural ordering");
            }
        }
        if ( cur == null) { return null; }
        return cur;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * Time complexity is O(h) overall, where h is the height of the tree
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        //TODO: Left as an exercise.
        if ( root == null ) {
            root = new BTNode<>(new Entry<>(key, value));
            this.currentSize++;
            return null;
        }
        V res = null;
        BTNode<Entry<K,V>> cur = (BTNode<Entry<K,V>>) root;
        while (!isKey(cur, key)) {
            Entry<K,V> elm = cur.getElement();
            int comparison = key.compareTo(elm.key());
            if ( comparison < 0 ) {
                BTNode<Entry<K,V>> newEl = (BTNode<Entry<K,V>>)cur.getLeftChild();
                if ( newEl == null ) {
                    cur.setLeftChild(new BTNode<>(new Entry<>(key, value), cur));
                    this.currentSize++;
                    return null;
                }
                cur = newEl;
            } else if ( comparison > 0 ) {
                BTNode<Entry<K,V>> newEl = (BTNode<Entry<K,V>>)cur.getRightChild();
                if ( newEl == null ) {
                    cur.setRightChild(new BTNode<>(new Entry<>(key, value), cur));
                    this.currentSize++;
                    return null;
                }
                cur = newEl;
            } else {
                throw new RuntimeException("Not a natural ordering");
            }
        }
        res = cur.getElement().value();
        cur.setElement(new Entry<>(key, value));
        return res;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * Time complexity is overall O(h) where h is the height of the tree
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        //TODO: Left as an exercise.
        BTNode<Entry<K, V>> node = getNode((BTNode<Entry<K,V>>) root, key);
        if ( node == null ) { return null; }
        BTNode<Entry<K,V>> parent = ((BTNode<Entry<K,V>>) node.getParent());
        V val = node.getElement().value();
        int children = 0;
        if ( node.getLeftChild() != null ) { children++; }
        if ( node.getRightChild() != null ) { children++; }
        if ( children == 0 ) {
            assert node.isLeaf();
            if ( parent == null ) {
                this.root = null;
            } else {
                if ( node.getElement().key().compareTo(parent.getElement().key()) < 0 ) {
                    parent.setLeftChild(null);
                } else {
                    parent.setRightChild(null);
                }
            }
        }
        else if ( children == 1 ) {
            BTNode<Entry<K,V>> replacingWith;
            if ( node.getLeftChild() != null && node.getRightChild() == null) {
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
            replacingWith.setParent(parent);

        }
        else {
            BTNode<Entry<K,V>> toReplace = ((BTNode<Entry<K,V>>) node.getLeftChild()).furtherRightElement();
            node.setElement(toReplace.getElement());
            toReplace.setElement(null);
            BTNode<Entry<K,V>> replaceParent = (BTNode<Entry<K,V>>)toReplace.getParent();
            if ( replaceParent.getLeftChild() == toReplace ) {
                replaceParent.setLeftChild(toReplace.getLeftChild());
                if ( replaceParent.getLeftChild() != null) {
                    ((BTNode<Entry<K,V>>)replaceParent.getLeftChild()).setParent(replaceParent);
                }
            } else {
                assert replaceParent.getRightChild() == toReplace;
                replaceParent.setRightChild(toReplace.getLeftChild());
                if ( replaceParent.getRightChild() != null) {
                    ((BTNode<Entry<K,V>>)replaceParent.getRightChild()).setParent(replaceParent);
                }
            }
            toReplace.setParent(null);
        }
        this.currentSize--;
        return val;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}
