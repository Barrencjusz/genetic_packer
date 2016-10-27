package genetic.api.individual.impl;

import genetic.api.builder.HasBuilder;

/**
 * @author piotr.larysz
 */
public class DetailedIndividual<V extends Comparable<V>, T> extends RatedIndividual<V, T> {

    private Integer numberOfGeneration;

    @HasBuilder
    public DetailedIndividual(Integer numberOfGeneration, RatedIndividual<V, T> ratedIndividual) {
        super(ratedIndividual.getFitness(), ratedIndividual);
        this.numberOfGeneration = numberOfGeneration;
    }

    public Integer getNumberOfGeneration() {
        return numberOfGeneration;
    }
}
