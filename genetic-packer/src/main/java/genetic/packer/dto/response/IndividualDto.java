package genetic.packer.dto.response;

import genetic.api.builder.HasBuilder;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class IndividualDto {

    private Double fitness;

    private Integer numberOfGeneration;

    private Seq<TranslatedBoxDto> translatedBoxes;

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Integer getNumberOfGeneration() {
        return numberOfGeneration;
    }

    public void setNumberOfGeneration(Integer numberOfGeneration) {
        this.numberOfGeneration = numberOfGeneration;
    }

    public Seq<TranslatedBoxDto> getTranslatedBoxes() {
        return translatedBoxes;
    }

    public void setTranslatedBoxes(Seq<TranslatedBoxDto> translatedBoxes) {
        this.translatedBoxes = translatedBoxes;
    }
}
