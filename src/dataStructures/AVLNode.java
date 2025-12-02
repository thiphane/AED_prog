package dataStructures;
/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }
    
    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        super(element, parent, left, right);
        this.updateHeight();
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        this.height= 0;
    }

    protected void updateHeight() {
        this.height = 1 + Math.max(height((AVLNode<E>)this.getLeftChild()), height((AVLNode<E>)this.getRightChild()));
        if ( this.getParent() != null ) {
            ((AVLNode<E>)this.getParent()).updateHeight();
        }
    }

    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        //TODO: Left as an exercise.
        super.setLeftChild(node);
        this.updateHeight();
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        //TODO: Left as an exercise.
        super.setRightChild(node);
        this.updateHeight();
    }
// others public methods
//TODO: Left as an exercise.

    public boolean isUnbalanced() {
        return Math.abs(height((AVLNode<E>) this.getLeftChild()) - height((AVLNode<E>) this.getRightChild())) > 1;
    }

    @Override
    public void setRightChild(Node<E> node) {
        this.setRightChild((AVLNode<E>)node);
    }

    @Override
    public void setLeftChild(Node<E> node) {
        this.setLeftChild((AVLNode<E>) node);
    }
}
