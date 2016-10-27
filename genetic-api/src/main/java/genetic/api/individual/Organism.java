package genetic.api.individual;

import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
public interface Organism<T> {

    Seq<Cell<T>> getCells();
}
