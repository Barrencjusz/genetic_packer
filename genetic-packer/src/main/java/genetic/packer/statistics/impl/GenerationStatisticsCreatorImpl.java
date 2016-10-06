package genetic.packer.statistics.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;

import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import genetic.packer.evolution.generation.dto.GenerationStatisticsBuilder;
import genetic.packer.evolution.generation.dto.Individual;
import genetic.packer.statistics.GenerationStatisticsCreator;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class GenerationStatisticsCreatorImpl implements GenerationStatisticsCreator {

    @Autowired
    private Function<Generation<Double, Individual<Box>>, Double> bestFitnessResolver;

    @Autowired
    private Function<Generation<Double, Individual<Box>>, Double> averageFitnessResolver;

    @Override
    public List<GenerationStatistics> create(List<Generation<Double, Individual<Box>>> generations) {
        return generations.stream().map(this::createGenerationStatistics).collect(toList());
    }

    private GenerationStatistics createGenerationStatistics(Generation<Double, Individual<Box>> generation) {
        return new GenerationStatisticsBuilder()
            .withBestFitness(this.bestFitnessResolver.apply(generation))
            .withAverageFitness(this.averageFitnessResolver.apply(generation))
            .build();
    }
}
