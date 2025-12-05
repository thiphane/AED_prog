package dataStructures;

public class TransformerIterator<F,T> implements Iterator<T> {
    protected final Transformer<F,T> transformer;
    protected final Iterator<F> inner;

    public TransformerIterator(Iterator<F> inner, Transformer<F,T> transformer) {
        this.inner = inner;
        this.transformer = transformer;
    }

    @SuppressWarnings("unchecked")
    public TransformerIterator(Iterator<F> inner) {
        // Simply cast F to T if no transformer is given
        this(inner, (s) -> (T)s);
    }

    // Same time complexity as the inner iterator
    @Override
    public boolean hasNext() {
        return inner.hasNext();
    }

    /**
     * The time complexity will be the highest between
     * the transformer's transform() and the iterator's next()
     */
    @Override
    public T next() {
        return transformer.transform(inner.next());
    }

    /**
     * The same time complexity as the inner iterator
     */
    @Override
    public void rewind() {
        this.inner.rewind();
    }
}
