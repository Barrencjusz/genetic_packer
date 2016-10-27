package genetic.api.fitness;

import java.util.function.Function;

import genetic.api.individual.Individual;
import genetic.api.individual.impl.RatedIndividual;

/**
 * @author piotr.larysz
 */
public interface Evaluator<T1 extends Comparable<T1>, T2> extends Function<Individual<T1, T2>, RatedIndividual<T1, T2>> {

}
