package genetic.packer.evolution.generation.dto.individual;

import java.util.function.Function;

import genetic.packer.evolution.generation.dto.individual.impl.RatedIndividual;

/**
 * @author piotr.larysz
 */
public interface Evaluated<T1 extends Comparable<T1>, T2> {

    RatedIndividual<T1, T2> evaluate(Function<Individual<T2>, T1> testingFunction);
}
