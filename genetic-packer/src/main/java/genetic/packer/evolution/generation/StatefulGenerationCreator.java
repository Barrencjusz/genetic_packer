package genetic.packer.evolution.generation;

import static java.util.stream.Collectors.toList;

import com.google.common.collect.Iterables;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationBuilder;
import genetic.packer.evolution.generation.dto.Individual;
import genetic.selectors.dto.RatedIndividual;
import genetic.selectors.dto.RatedIndividualBuilder;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype")
public class StatefulGenerationCreator implements Supplier<Generation<Double, Individual<Box>>> {

    @Autowired
    private BiFunction<Embryo, Integer, Collection<Individual<Box>>> firstGenerationCreator;

    @Autowired
    private BiFunction<Generation<Double, Individual<Box>>, GeneticContext, Collection<Individual<Box>>> ongoingGenerationCreator;

    @Autowired
    private Supplier<Integer> idGenerator;

    @Autowired
    private BiFunction<Individual<Box>, Bounds, Individual<Box>> normalizer;

    @Autowired
    private Function<Individual<Box>, Double> fitnessTester;

    private List<Generation<Double, Individual<Box>>> subsequentGenerations = new ArrayList<>();

    private GeneticContext context;

    @Autowired
    public StatefulGenerationCreator(GeneticContext context) {
        this.context = context;
    }

    private final Supplier<Collection<Individual<Box>>> ongoingStrategy = () -> {
        final Generation<Double, Individual<Box>> lastGeneration = Iterables.getLast(subsequentGenerations);
        return this.ongoingGenerationCreator.apply(lastGeneration, context);
    };

    private final Supplier<Collection<Individual<Box>>> firstTimeStrategy = () -> {
        this.strategy = ongoingStrategy;
        return this.firstGenerationCreator.apply(context.getEmbryo(), context.getGenerationSize());
    };

    private Supplier<Collection<Individual<Box>>> strategy = firstTimeStrategy;

    @Override
    public Generation<Double, Individual<Box>> get() {
        final Collection<Individual<Box>> individuals = strategy.get();
        final List<Individual<Box>> normalizedIndividuals = individuals.stream()
            .map(individual -> this.normalizer.apply(individual, this.context.getEmbryo().getBounds()))
            .collect(toList());
        final Collection<RatedIndividual<Double, Individual<Box>>> ratedIndividuals = rateIndividuals(normalizedIndividuals);
        final Generation<Double, Individual<Box>> generation = new GenerationBuilder<Double, Individual<Box>>()
                .withRatedIndividuals(ratedIndividuals)
                .withRawIndividuals(individuals)
                .withId(idGenerator.get())
                .build();
        this.subsequentGenerations.add(generation);
        return generation;
    }

    private Collection<RatedIndividual<Double, Individual<Box>>> rateIndividuals(Collection<Individual<Box>> individuals) {
        return individuals
            .stream()
            .map(individual -> new RatedIndividualBuilder<Double, Individual<Box>>().withFitness(fitnessTester.apply(individual)).withIndividual(individual).build())
            .collect(toList());
    }
}