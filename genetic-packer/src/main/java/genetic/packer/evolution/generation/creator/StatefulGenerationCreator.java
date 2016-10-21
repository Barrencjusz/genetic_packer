package genetic.packer.evolution.generation.creator;

import static java.util.stream.Collectors.toList;

import com.google.common.collect.Iterables;
import genetic.packer.evolution.generation.GeneticContext;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.GenerationBuilder;
import genetic.packer.evolution.generation.dto.individual.Evaluated;
import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import genetic.packer.evolution.generation.dto.individual.impl.RatedIndividual;
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
public class StatefulGenerationCreator implements Supplier<Generation<Double, SimpleIndividual<Box>>> {

    @Autowired
    private BiFunction<Embryo, Integer, Collection<SimpleIndividual<Box>>> firstGenerationCreator;

    @Autowired
    private BiFunction<Generation<Double, SimpleIndividual<Box>>, GeneticContext, Collection<SimpleIndividual<Box>>> ongoingGenerationCreator;

    @Autowired
    private Supplier<Integer> idGenerator;

    @Autowired
    private BiFunction<SimpleIndividual<Box>, Bounds, SimpleIndividual<Box>> normalizer;

    @Autowired
    private Function<SimpleIndividual<Box>, Double> fitnessTester;

    private List<Generation<Double, SimpleIndividual<Box>>> subsequentGenerations = new ArrayList<>();

    private GeneticContext context;

    @Autowired
    public StatefulGenerationCreator(GeneticContext context) {
        this.context = context;
    }

    private final Supplier<Collection<SimpleIndividual<Box>>> ongoingStrategy = () -> {
        final Generation<Double, SimpleIndividual<Box>> lastGeneration = Iterables.getLast(subsequentGenerations);
        return this.ongoingGenerationCreator.apply(lastGeneration, context);
    };

    private final Supplier<Collection<SimpleIndividual<Box>>> firstTimeStrategy = () -> {
        this.strategy = ongoingStrategy;
        return this.firstGenerationCreator.apply(context.getEmbryo(), context.getGenerationSize());
    };

    private Supplier<Collection<SimpleIndividual<Box>>> strategy = firstTimeStrategy;

    @Override
    public Generation<Double, SimpleIndividual<Box>> get() {
        final Collection<SimpleIndividual<Box>> individuals = strategy.get();
//        final List<SimpleIndividual<Box>> normalizedIndividuals = individuals.stream()
//            .map(individual -> this.normalizer.apply(individual, this.context.getEmbryo().getBounds()))
//            .collect(toList());
        final Collection<RatedIndividual<Double, SimpleIndividual<Box>>> ratedIndividuals = rateIndividuals(individuals);
        final Generation<Double, SimpleIndividual<Box>> generation = new GenerationBuilder<Double, SimpleIndividual<Box>>()
                .withRatedIndividuals(ratedIndividuals)
                .withRawIndividuals(individuals)
                .withId(idGenerator.get())
                .build();
        this.subsequentGenerations.add(generation);
        return generation;
    }

    private Collection<RatedIndividual<Double, SimpleIndividual<Box>>> rateIndividuals(Collection<Evaluated> individuals) {
        return individuals
            .stream()
            .map(individual -> individual.evaluate(fitnessTester))
            .collect(toList());
    }
}