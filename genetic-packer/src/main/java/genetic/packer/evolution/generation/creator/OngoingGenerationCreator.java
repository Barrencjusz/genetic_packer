package genetic.packer.evolution.generation.creator;

import java.util.function.BiFunction;

import com.google.common.collect.Iterables;
import genetic.api.individual.Individual;
import genetic.api.individual.Organism;
import genetic.api.individual.impl.RatedIndividual;
import genetic.packer.evolution.elitism.ElitistPicker;
import genetic.packer.evolution.generation.GeneticContext;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.mutation.Mutator;
import genetic.recombinators.Recombinator;
import genetic.selectors.PairSelector;
import javafx.scene.shape.Box;
import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.collection.Traversable;
import javaslang.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class OngoingGenerationCreator implements BiFunction<Generation<Double, Box>, GeneticContext, Traversable<Individual<Double, Box>>> {

    @Autowired
    private ElitistPicker elitistPicker; //todo feature toggle for cloning?

    @Autowired
    private PairSelector.Creator<Double> pairSelectorCreator;

    @Autowired
    private Recombinator<Organism<Box>, Individual<Double, Box>> singleChildRecombinator;

    @Autowired
    private Recombinator<Organism<Box>, Individual<Double, Box>> twoChildrenRecombinator;

    @Autowired
    private Mutator mutator;

    @Override
    public Traversable<Individual<Double, Box>> apply(Generation<Double, Box> generation, GeneticContext evolutionContext) {

        final Seq<Individual<Double, Box>> elites = this.elitistPicker.pick(generation.getRatedIndividuals(), evolutionContext.getNumberOfEliteIndividuals()); //fixme remove sorting inside

        final int noIndividualsToGenerate = (generation.getRatedIndividuals().size() - elites.size()) / 2;
        final boolean numberOfNewIndividualsIsOdd = (generation.getRatedIndividuals().size() - noIndividualsToGenerate) % 2 != 0; //todo method (misc), check if proper

        final PairSelector<RatedIndividual<Double, Box>> pairSelector = this.pairSelectorCreator.from(generation.getRatedIndividuals());

        final Seq<Individual<Double, Box>> newIndividuals = pairSelector.select(size -> noIndividualsToGenerate)
                .flatMap(this.twoChildrenRecombinator)
                .appendAll(this.createSingleForEven(pairSelector, numberOfNewIndividualsIsOdd))
                .peek(individual ->  this.mutator.accept(individual, evolutionContext.getEmbryo().getBounds()));

        return List.<Individual<Double, Box>> empty().appendAll(newIndividuals).appendAll(elites);
    }

    private Iterable<Individual<Double,Box>> createSingleForEven(PairSelector<RatedIndividual<Double, Box>> pairSelector, boolean numberOfNewIndividualsIsOdd) {
        if(numberOfNewIndividualsIsOdd) {
            return Option.of(pairSelector.select(size -> 1))
                    .map(Iterables::getOnlyElement)
                    .map(singleChildRecombinator)
                    .map(Iterables::getOnlyElement);
        }
        return Option.none();
    }
}
