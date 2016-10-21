package genetic.recombinators;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author piotr.larysz
 */
public interface Recombinator<T1, T2> extends Function<Pair<T1, T1>, T2> {

}
