package dataStructures;

public interface Transformer<F,T> {
    T transform(F value);
}
