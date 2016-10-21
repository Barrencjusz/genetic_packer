package genetic.packer.evolution.elitism;

import java.util.Collection;
import java.util.List;

import genetic.selectors.dto.FitnessTested;

/**
 * @author piotr.larysz
 */
public interface ElitistPicker {

    <M1 extends Comparable<M1>, M2 extends FitnessTested<M1>> List<M2> pick(Collection<M2> fitnessTesteds, Integer count);
}
