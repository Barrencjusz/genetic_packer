package genetic.api.fitness;

import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
public interface Fitness<T extends Comparable<T>> extends Supplier<T> {

    String explain();
}
