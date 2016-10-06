package genetic.packer.evolution.generation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.Individual;
import genetic.packer.evolution.recombination.Recombinator;
import genetic.packer.misc.Sorting;
import genetic.selectors.PairSelector;
import genetic.selectors.dto.RatedIndividual;
import javafx.scene.shape.Box;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class OngoingGenerationCreator implements BiFunction<Generation<Double, Individual<Box>>, GeneticContext, Collection<Individual<Box>>> {

    @Autowired
    private ElitistPicker<Individual<Box>> elitistPicker; //todo feature toggle for cloning?

    @Autowired
    private PairSelector.Creator<Double> pairSelectorCreator;

    @Autowired
    private Recombinator<Individual<Box>, Pair<Individual<Box>, Individual<Box>>> twoChildrenRecombinator;

    @Autowired
    private Recombinator<Individual<Box>, Individual<Box>> singleChildRecombinator;

    @Autowired
    private Consumer<Individual<Box>> mutator;

    @Override
    public Collection<Individual<Box>> apply(Generation<Double, Individual<Box>> generation, GeneticContext evolutionContext) {
        //todo think about sorting the individuals earlier (think)
        List<RatedIndividual<Double, Individual<Box>>> sortedIndividuals = generation.getRatedIndividuals().stream()
            .sorted(Sorting::compareDescending)
            .collect(Collectors.toList());

        final List<Individual<Box>> elites = this.elitistPicker.pick(sortedIndividuals, evolutionContext.getNumberOfEliteIndividuals()); //fixme remove sorting inside

        final int noIndividualsToGenerate = (sortedIndividuals.size() - elites.size()) / 2;
        final boolean numberOfNewIndividualsIsOdd = (sortedIndividuals.size() - noIndividualsToGenerate) % 2 != 0; //todo method (misc), check if proper

        PairSelector.SizeFunction eliteAwareSize = size -> noIndividualsToGenerate;

        final PairSelector<Double, Individual<Box>> pairSelector = this.pairSelectorCreator.from(generation.getRatedIndividuals());

        final List<Individual<Box>> newIndividuals = pairSelector.select(eliteAwareSize).stream()
                .map(ratedIndividual -> new ImmutablePair<>(ratedIndividual.getLeft().get(), ratedIndividual.getRight().get()))
                .map(this.twoChildrenRecombinator)
                .flatMap(pair -> ImmutableList.of(pair.getKey(), pair.getValue()).stream())
                .peek(mutator)
                .collect(Collectors.toList());
        newIndividuals.addAll(elites); //todo clone ?

        if(numberOfNewIndividualsIsOdd) { //fixme maybe take the size from above
            Optional.of(pairSelector.select(size -> 1))
                .map(Iterables::getOnlyElement)
                .map(pair -> new ImmutablePair<>(pair.getLeft().get(), pair.getRight().get()))
                .map(singleChildRecombinator)
                .ifPresent(newIndividuals::add);
        }
        return newIndividuals;
    }
}
