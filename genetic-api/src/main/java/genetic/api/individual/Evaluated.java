package genetic.api.individual;

import genetic.api.elitism.EliteAcknowledged;
import genetic.api.fitness.Evaluator;
import genetic.api.individual.impl.RatedIndividual;

/**
 * @author piotr.larysz
 */
public interface Evaluated<T1 extends Comparable<T1>, T2> {

    <M extends Evaluator<T1, T2> & EliteAcknowledged> RatedIndividual<T1, T2> evaluate(M evaluator);
}
