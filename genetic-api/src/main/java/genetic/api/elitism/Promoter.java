package genetic.api.elitism;

import genetic.api.individual.impl.Elite;
import genetic.api.individual.impl.RatedIndividual;

/**
 * @author piotr.larysz
 */
public interface Promoter {

    <M1 extends Comparable<M1>, M2> Elite<M1, M2> apply(RatedIndividual<M1, M2> ratedIndividual);
}
