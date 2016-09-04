package genetic.packer.generation.dto;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class GenerationStatistics {

    private Integer generationNumber;

    private Double bestFitness;

    public Integer getGenerationNumber() {
        return generationNumber;
    }

    public void setGenerationNumber(Integer generationNumber) {
        this.generationNumber = generationNumber;
    }

    public Double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(Double bestFitness) {
        this.bestFitness = bestFitness;
    }
}
