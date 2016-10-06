package genetic.packer.evolution.elitism.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.misc.Sorting;
import genetic.selectors.dto.RatedIndividual;

/**
 * @author piotr.larysz
 */
public class ElitistPickerImpl<T> implements ElitistPicker<T> {

    @Override
    public List<T> pick(Collection<RatedIndividual<Double, T>> individuals, Integer count) {
        return individuals.stream()
            .sorted(Sorting::compareDescending)
            .limit(count)
            .map(RatedIndividual::get)
            .collect(Collectors.toList());
    }
}
