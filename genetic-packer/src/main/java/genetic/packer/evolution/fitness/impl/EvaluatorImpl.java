package genetic.packer.evolution.fitness.impl;

import java.util.function.Function;

import genetic.api.elitism.EliteAcknowledged;
import genetic.api.fitness.Evaluator;
import genetic.api.individual.Individual;
import genetic.api.individual.impl.RatedIndividual;
import genetic.api.individual.impl.RatedIndividualBuilder;
import genetic.packer.evolution.fitness.FitnessComponents;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class EvaluatorImpl implements Evaluator<Double, Box>, EliteAcknowledged {

    @Autowired
    @Qualifier("fitnessTesterImpl")
    private Function<Individual<Double, Box>, FitnessComponents> fitnessTester;

    @Override
    public RatedIndividual<Double, Box> apply(Individual<Double, Box> individual) {
        return new RatedIndividualBuilder<Double, Box>()
            .fitness(fitnessTester.apply(individual))
            .organism(individual)
            .build();
    }
}
