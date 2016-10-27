package genetic.api.elitism;

import genetic.api.individual.impl.Elite;

/**
 * @author piotr.larysz
 */
public interface EliteAcknowledged {

    default <M1 extends Comparable<M1>, M2> Elite<M1, M2> acknowledge(Elite<M1, M2> elite) {
        return elite;
    }
}
