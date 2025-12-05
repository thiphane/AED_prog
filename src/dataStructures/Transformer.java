package dataStructures;

import java.io.Serializable;

public interface Transformer<F,T> extends Serializable {
    T transform(F value);
}
