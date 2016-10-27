package genetic.packer.evolution.fitness.impl;

import genetic.api.elitism.EliteAcknowledged;
import genetic.api.fitness.Evaluator;
import genetic.api.individual.Individual;
import genetic.api.individual.impl.RatedIndividual;
import genetic.api.individual.impl.RatedIndividualBuilder;
import javafx.scene.shape.Box;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class EvaluatorImpl implements Evaluator<Double, Box>, EliteAcknowledged {

    @Override
    public RatedIndividual<Double, Box> apply(Individual<Double, Box> individual) {
        return new RatedIndividualBuilder<Double, Box>()
            .fitness(5.0) //fixme lol
            .organism(individual)
            .build();
    }
}
