package genetic.recombinators;

import java.util.function.Function;

import javaslang.Tuple2;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public interface Recombinator<T1, T2> extends Function<Tuple2<? extends T1, ? extends T1>, Traversable<T2>> {

}
