package genetic.packer.statistics.best;

import java.util.function.Function;

import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.individual.Individual;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import genetic.packer.evolution.generation.dto.individual.impl.RatedIndividual;
import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class BestFitnessResolver implements Function<Generation<Double, Individual<Box>>, Double> {

    @Override
    public Double apply(Generation<Double, Individual<Box>> generation) {
        return generation.getRatedIndividuals().stream()
            .map(RatedIndividual::getFitness)
            .max(Double::compare)
            .get();
    }
}
