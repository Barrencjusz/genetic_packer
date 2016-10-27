package genetic.packer.evolution.generation.creator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.google.common.collect.Iterables;
import genetic.api.individual.Evaluated;
import genetic.api.individual.Individual;
import genetic.api.individual.impl.RatedIndividual;
import genetic.packer.evolution.fitness.impl.EvaluatorImpl;
import genetic.packer.evolution.generation.GeneticContext;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationBuilder;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype")
public class StatefulGenerationCreator implements Supplier<Generation<Double, Box>> {

    @Autowired
    BiFunction<Embryo, Integer, Traversable<Individual<Double, Box>>> firstGenerationCreator;

    @Autowired
    private BiFunction<Generation<Double, Box>, GeneticContext, Traversable<Individual<Double, Box>>> ongoingGenerationCreator;

    @Autowired
    private Supplier<Integer> idGenerator;

    @Autowired
    private BiFunction<Individual<Double, Box>, Bounds, Individual<Double, Box>> normalizer;

    @Autowired
    private EvaluatorImpl evaluator;

    private List<Generation<Double, Box>> subsequentGenerations = new ArrayList<>();

    private GeneticContext context;

    @Autowired
    public StatefulGenerationCreator(GeneticContext context) {
        this.context = context;
    }

    private final Supplier<Traversable<Individual<Double, Box>>> ongoingStrategy = () -> {
        return this.ongoingGenerationCreator.apply(Iterables.getLast(subsequentGenerations), context);
    };

    private final Supplier<Traversable<Individual<Double, Box>>> firstTimeStrategy = () -> {
        this.strategy = ongoingStrategy;
        return this.firstGenerationCreator.apply(context.getEmbryo(), context.getGenerationSize());
    };

    private Supplier<Traversable<Individual<Double, Box>>> strategy = firstTimeStrategy;

    @Override
    public Generation<Double, Box> get() {
        final Traversable<Individual<Double, Box>> individuals = strategy.get();
//        final List<SimpleIndividual<Box>> normalizedIndividuals = individuals.stream()
//            .map(individual -> this.normalizer.apply(individual, this.context.getEmbryo().getBounds()))
//            .collect(toList());
        final Traversable<RatedIndividual<Double, Box>> ratedIndividuals = rateIndividuals(individuals);
        final Generation<Double, Box> generation = new GenerationBuilder<Double, Box>()
                .ratedIndividuals(ratedIndividuals)
                //.rawIndividuals(individuals)
                .id(idGenerator.get())
                .build();
        this.subsequentGenerations.add(generation);
        return generation;
    }

    private Traversable<RatedIndividual<Double, Box>> rateIndividuals(Traversable<? extends Evaluated<Double, Box>> individuals) {
        return individuals.map(individual -> individual.evaluate(evaluator));
    }
}