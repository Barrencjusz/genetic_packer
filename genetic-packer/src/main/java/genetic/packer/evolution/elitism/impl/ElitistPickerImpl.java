package genetic.packer.evolution.elitism.impl;

import genetic.api.individual.Individual;
import genetic.api.individual.impl.RatedIndividual;
import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.misc.Sorting;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class ElitistPickerImpl implements ElitistPicker {

    @Autowired
    private PromoterImpl promoter;

    @Override
    public <M1 extends Comparable<M1>, M2> Seq<Individual<M1, M2>> pick(Traversable<RatedIndividual<M1, M2>> fitnessTesteds, Integer count) {
        return fitnessTesteds.toStream()
            .sorted(Sorting::compareDescending)
            .take(count)
            .map(ratedIndividual -> ratedIndividual.promote(promoter));
    }
}
