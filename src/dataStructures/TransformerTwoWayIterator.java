package dataStructures;

public class TransformerTwoWayIterator<F,T> extends TransformerIterator<F,T> implements TwoWayIterator<T> {
    public TransformerTwoWayIterator(TwoWayIterator<F> inner, Transformer<F,T> transformer) {
        super(inner, transformer);
    }

    public TransformerTwoWayIterator(TwoWayIterator<F> inner) {
        super(inner);
    }

    @Override
    public boolean hasPrevious() {
        return ((TwoWayIterator<F>)this.inner).hasPrevious();
    }

    @Override
    public T previous() {
        return transformer.transform(((TwoWayIterator<F>)this.inner).previous());
    }

    @Override
    public void fullForward() {
        ((TwoWayIterator<F>)this.inner).fullForward();
    }
}
