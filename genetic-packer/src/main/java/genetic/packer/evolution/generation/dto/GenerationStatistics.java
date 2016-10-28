package genetic.packer.evolution.generation.dto;

import genetic.api.builder.HasBuilder;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class GenerationStatistics {

    private Integer generationNumber;

    private Double bestFitness;

    private Double averageFitness;

    private String bestFitnessExplained; //todo some generic self-explaining function?

    private Double averageNaturalFitness;

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

    public Double getAverageFitness() {
        return averageFitness;
    }

    public void setAverageFitness(Double averageFitness) {
        this.averageFitness = averageFitness;
    }

    public String getBestFitnessExplained() {
        return bestFitnessExplained;
    }

    public void setBestFitnessExplained(String bestFitnessExplained) {
        this.bestFitnessExplained = bestFitnessExplained;
    }

    public Double getAverageNaturalFitness() {
        return averageNaturalFitness;
    }

    public void setAverageNaturalFitness(Double averageNaturalFitness) {
        this.averageNaturalFitness = averageNaturalFitness;
    }
}
