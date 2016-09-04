package genetic.packer.generation;

import com.google.common.collect.ImmutableList;
import genetic.packer.generation.dto.Generation;
import genetic.packer.generation.dto.Individual;
import genetic.packer.recombination.Recombinator;
import genetic.selectors.PairSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class OngoingGenerationCreator implements BiFunction<Generation, GeneticContext, Collection<Individual>> {

    @Autowired
    private PairSelector<Double> pairSelector;

    @Autowired
    private Recombinator<Individual, GeneticContext> recombinator;

    @Autowired
    private Consumer<Individual> mutator;

    @Override
    public Collection<Individual> apply(Generation generation, GeneticContext evolutionContext) {
        final PairSelector.Context<Double, Individual> context = new PairSelector.Context<>(generation.getRatedIndividuals(), PairSelector.Context.HALF);
        final Collection<Map.Entry<Individual, Individual>> selectedPairs = pairSelector.select(context);
        return selectedPairs.stream()
                .map(recombinate(evolutionContext)) //FIXME return function by recombinator?
                .flatMap(pair -> ImmutableList.of(pair.getKey(), pair.getValue()).stream())
                .peek(mutator) //FIXME context
                .collect(Collectors.toList());
    }

    private Function<Map.Entry<Individual, Individual>, Map.Entry<Individual, Individual>> recombinate(GeneticContext evolutionContext) {
        return entry -> recombinator.apply(entry, evolutionContext);
    }
}
