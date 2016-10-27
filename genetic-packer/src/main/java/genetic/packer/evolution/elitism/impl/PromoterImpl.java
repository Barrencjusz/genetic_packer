package genetic.packer.evolution.elitism.impl;

import genetic.api.elitism.EliteAcknowledged;
import genetic.api.elitism.Promoter;
import genetic.api.individual.impl.Elite;
import genetic.api.individual.impl.RatedIndividual;

/**
 * @author piotr.larysz
 */
public class PromoterImpl implements Promoter, EliteAcknowledged {

    @Override
    public <M1 extends Comparable<M1>, M2> Elite<M1, M2> acknowledge(Elite<M1, M2> elite) {
        return elite;
    }

    @Override
    public <M1 extends Comparable<M1>, M2> Elite<M1, M2> apply(RatedIndividual<M1, M2> ratedIndividual) {
        return new Elite<>(ratedIndividual);
    }
}
