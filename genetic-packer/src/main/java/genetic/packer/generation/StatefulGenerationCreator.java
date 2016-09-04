package genetic.packer.generation;

import com.google.common.collect.Iterables;
import genetic.packer.generation.dto.Embryo;
import genetic.packer.generation.dto.Generation;
import genetic.packer.generation.dto.GenerationBuilder;
import genetic.packer.generation.dto.Individual;
import genetic.selectors.dto.RatedIndividual;
import genetic.selectors.dto.RatedIndividualBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
@Scope(value = "prototype")
public class StatefulGenerationCreator implements Supplier<Generation> {

    @Autowired
    private BiFunction<Embryo, Integer, Collection<Individual>> firstGenerationCreator;

    @Autowired
    private BiFunction<Generation, GeneticContext, Collection<Individual>> ongoingGenerationCreator;

    @Autowired
    private Supplier<Integer> idGenerator;

    @Autowired
    private Function<Individual, Double> fitnessTester;

    private List<Generation> subsequentGenerations = new ArrayList<>();

    private GeneticContext context;

    @Autowired
    public StatefulGenerationCreator(GeneticContext context) {
        this.context = context;
    }

    private final Supplier<Collection<Individual>> ongoingStrategy = () -> {
        final Generation lastGeneration = Iterables.getLast(subsequentGenerations);
        return this.ongoingGenerationCreator.apply(lastGeneration, context);
    };

    private final Supplier<Collection<Individual>> firstTimeStrategy = () -> {
        this.strategy = ongoingStrategy;
        return this.firstGenerationCreator.apply(context.getEmbryo(), context.getGenerationSize());
    };

    private Supplier<Collection<Individual>> strategy = firstTimeStrategy;

    @Override
    public Generation get() {
        final Collection<RatedIndividual<Double, Individual>> ratedIndividuals = rateIndividuals(strategy.get());
        return addGeneration(new GenerationBuilder<Double, Individual>().withRatedIndividuals(ratedIndividuals).withId(idGenerator.get()).build());
    }

    private Generation addGeneration(Generation generation) {
        this.subsequentGenerations.add(generation);
        return generation;
    }

    private Collection<RatedIndividual<Double, Individual>> rateIndividuals(Collection<Individual> individuals) {
        return individuals
                .stream()
                .map(individual -> new RatedIndividualBuilder<Double, Individual>().withFitness(fitnessTester.apply(individual)).withIndividual(individual).build())
                .collect(Collectors.toList());
    }
}