package genetic.packer.evolution.fitness;

import java.util.function.Function;

/**
 * @author piotr.larysz
 */
public interface FitnessTranslator<T1, T2> extends Function<T1, T2> {
}
