package genetic.packer.evolution.elitism.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.misc.Sorting;
import genetic.selectors.dto.FitnessTested;

/**
 * @author piotr.larysz
 */
public class ElitistPickerImpl<T> implements ElitistPicker {

    @Override
    public <M1 extends Comparable<M1>, M2 extends FitnessTested<M1>> List<M2> pick(Collection<M2> fitnessTesteds, Integer count) {
        return fitnessTesteds.stream()
            .sorted(Sorting::compareDescending)
            .limit(count)
            .collect(Collectors.toList());
    }
}
