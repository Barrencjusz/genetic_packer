package genetic.packer.evolution.elitism;

import java.util.Collection;
import java.util.List;

import genetic.selectors.dto.RatedIndividual;

/**
 * @author piotr.larysz
 */
public interface ElitistPicker<T> {

    List<T> pick(Collection<RatedIndividual<Double, T>> individuals, Integer count);
}
