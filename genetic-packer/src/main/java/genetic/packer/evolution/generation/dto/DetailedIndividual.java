package genetic.packer.evolution.generation.dto;

import genetic.selectors.dto.RatedIndividual;
import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
public class DetailedIndividual<V extends Comparable<V>, T> extends RatedIndividual<V, T> {

    private Integer numberOfGeneration;

    @GeneratePojoBuilder
    public DetailedIndividual(RatedIndividual<V, T> ratedIndividual) {
        super(ratedIndividual.get());
        this.setFitness(ratedIndividual.getFitness());
    }

    public Integer getNumberOfGeneration() {
        return numberOfGeneration;
    }

    public void setNumberOfGeneration(Integer numberOfGeneration) {
        this.numberOfGeneration = numberOfGeneration;
    }
}
