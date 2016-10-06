package genetic.packer.misc;

import genetic.selectors.dto.RatedIndividual;

/**
 * @author piotr.larysz
 */
public class Sorting {

    public static int compareDescending(RatedIndividual<Double, ?> first, RatedIndividual<Double, ?> second) {
        return Double.compare(second.getFitness(), first.getFitness());
    }
}
