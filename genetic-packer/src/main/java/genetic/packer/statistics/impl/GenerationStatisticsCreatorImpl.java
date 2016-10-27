package genetic.packer.statistics.impl;

import java.util.function.Function;

import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationStatistics;
import genetic.packer.evolution.generation.dto.GenerationStatisticsBuilder;
import genetic.packer.statistics.GenerationStatisticsCreator;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class GenerationStatisticsCreatorImpl implements GenerationStatisticsCreator {

    @Autowired
    private Function<Generation<Double, Box>, Double> bestFitnessResolver;

    @Autowired
    private Function<Generation<Double, Box>, Double> averageFitnessResolver;

    @Override
    public Seq<GenerationStatistics> create(Seq<Generation<Double, Box>> generations) {
        return generations.map(this::createGenerationStatistics);
    }

    private GenerationStatistics createGenerationStatistics(Generation<Double, Box> generation) {
        return new GenerationStatisticsBuilder()
            .bestFitness(this.bestFitnessResolver.apply(generation))
            .averageFitness(this.averageFitnessResolver.apply(generation))
            .build();
    }
}
