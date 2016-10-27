package genetic.packer.dto.response;

import genetic.api.builder.HasBuilder;
import genetic.packer.dto.BoxDto;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import javaslang.collection.Seq;

/**
 * @author piotr.larysz
 */
@HasBuilder
public class ResponseDto {

    private BoxDto container;

    private Seq<IndividualDto> topIndividuals;

    private Seq<GenerationStatistics> generationStats;

    public BoxDto getContainer() {
        return container;
    }

    public void setContainer(BoxDto container) {
        this.container = container;
    }

    public Seq<IndividualDto> getTopIndividuals() {
        return topIndividuals;
    }

    public void setTopIndividuals(Seq<IndividualDto> topIndividuals) {
        this.topIndividuals = topIndividuals;
    }

    public Seq<GenerationStatistics> getGenerationStats() {
        return generationStats;
    }

    public void setGenerationStats(Seq<GenerationStatistics> generationStats) {
        this.generationStats = generationStats;
    }
}
