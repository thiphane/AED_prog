package dataStructures;

public class TransformerTwoWayIterator<F,T> extends TransformerIterator<F,T> implements TwoWayIterator<T> {
    public TransformerTwoWayIterator(TwoWayIterator<F> inner, Transformer<F,T> transformer) {
        super(inner, transformer);
    }

    public TransformerTwoWayIterator(TwoWayIterator<F> inner) {
        super(inner);
    }

    // Same time complexity as the regular iterator's hasNext
    @Override
    public boolean hasPrevious() {
        return ((TwoWayIterator<F>)this.inner).hasPrevious();
    }

    // Same time complexity as the regular iterator's next
    @Override
    public T previous() {
        return transformer.transform(((TwoWayIterator<F>)this.inner).previous());
    }

    // Same time complexity as the inner iterator
    @Override
    public void fullForward() {
        ((TwoWayIterator<F>)this.inner).fullForward();
    }
}
