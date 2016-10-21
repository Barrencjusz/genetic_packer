package genetic.packer.evolution.generation.dto.individual.impl;

import java.util.function.Function;

import genetic.packer.evolution.generation.dto.individual.Evaluated;
import genetic.packer.evolution.generation.dto.individual.Individual;

/**
 * @author piotr.larysz
 */
public class Elite<T1 extends Comparable<T1>, T2> extends RatedIndividual<T1, T2> implements Evaluated<T1, T2> {

    public Elite(T1 fitness, Individual<T2> individual) {
        super(fitness, individual);
    }

    @Override
    public RatedIndividual<T1, T2> evaluate(Function<Individual<T2>, T1> testingFunction) {
        return this;
    }
}
