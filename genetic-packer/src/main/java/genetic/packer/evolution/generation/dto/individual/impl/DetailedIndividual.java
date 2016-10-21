package genetic.packer.evolution.generation.dto.individual.impl;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class DetailedIndividual<V extends Comparable<V>, T> extends RatedIndividual<V, T> {

    private Integer numberOfGeneration;

    @GeneratePojoBuilder
    public DetailedIndividual(Integer numberOfGeneration, RatedIndividual<V, T> ratedIndividual) {
        super(ratedIndividual.getFitness(), ratedIndividual);
        this.numberOfGeneration = numberOfGeneration;
    }

    public Integer getNumberOfGeneration() {
        return numberOfGeneration;
    }
}
