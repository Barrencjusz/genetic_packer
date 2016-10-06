package genetic.packer.evolution.recombination;

import java.util.Collection;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author piotr.larysz
 */
public interface Recombinator<T, V> extends Function<Pair<T, T>, V> {


}
