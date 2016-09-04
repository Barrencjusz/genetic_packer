package genetic.packer.recombination;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author piotr.larysz
 */
public interface Recombinator<T, V> extends BiFunction<Map.Entry<T, T>, V, Map.Entry<T, T>> {


}
