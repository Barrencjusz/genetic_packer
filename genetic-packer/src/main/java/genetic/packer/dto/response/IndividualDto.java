package genetic.packer.dto.response;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;

/**
 * @author piotr.larysz
 */
@GeneratePojoBuilder
public class IndividualDto {

    private Double fitness;

    private Integer numberOfGeneration;

    private List<TranslatedBoxDto> translatedBoxes;

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

    public List<TranslatedBoxDto> getTranslatedBoxes() {
        return translatedBoxes;
    }

    public void setTranslatedBoxes(List<TranslatedBoxDto> translatedBoxes) {
        this.translatedBoxes = translatedBoxes;
    }
}
