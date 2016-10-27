package genetic.packer.evolution.elitism;

import genetic.api.individual.Individual;
import genetic.api.individual.impl.RatedIndividual;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;

/**
 * @author piotr.larysz
 */
public interface ElitistPicker {

    <M1 extends Comparable<M1>, M2> Seq<Individual<M1, M2>> pick(Traversable<RatedIndividual<M1, M2>> fitnessTesteds, Integer count);
}
