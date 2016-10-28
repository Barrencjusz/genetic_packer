package genetic.packer.statistics.average;

import java.util.function.Function;

import genetic.api.fitness.Fitness;
import genetic.api.individual.impl.RatedIndividual;
import genetic.packer.evolution.generation.dto.Generation;
import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class AverageFitnessResolver implements Function<Generation<Double, Box>, Double> {

    @Override
    public Double apply(Generation<Double, Box> generation) {
        return generation.getRatedIndividuals()
            .map(RatedIndividual::getFitness)
            .map(Fitness::get)
            .average()
            .get(); //fixme shitme
    }
}
