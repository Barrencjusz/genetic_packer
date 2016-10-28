package genetic.packer.statistics.best;

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
public class BestFitnessResolver implements Function<Generation<Double, Box>, Fitness<Double>> {

    @Override
    public Fitness<Double> apply(Generation<Double, Box> generation) {
        return generation.getRatedIndividuals()
            .maxBy((o1, o2) -> o1.getFitness().get().compareTo(o2.getFitness().get())) //fixme naming
            .map(RatedIndividual::getFitness)
            .get();
    }
}
