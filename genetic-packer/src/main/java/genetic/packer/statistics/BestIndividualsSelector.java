package genetic.packer.statistics;

import genetic.packer.evolution.generation.dto.DetailedIndividual;
import genetic.packer.evolution.generation.dto.DetailedIndividualBuilder;
import genetic.packer.evolution.generation.dto.Generation;
import genetic.packer.evolution.generation.dto.Individual;
import genetic.selectors.dto.RatedIndividual;
import javafx.scene.shape.Box;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Mapper
@Component//FIXME too long bifunction declaration
public abstract class BestIndividualsSelector implements BiFunction<List<Generation<Double, Individual<Box>>>, Integer, List<DetailedIndividual<Double, Individual<Box>>>> {

    @Override
    public List<DetailedIndividual<Double, Individual<Box>>> apply(List<Generation<Double, Individual<Box>>> generations, Integer size) {
        return generations
                .stream()
                .flatMap(this::toDetailedIndividuals)
                .sorted(this::compare)
                .limit(size)
                .collect(Collectors.toList());
    }

    private <T extends RatedIndividual<Double, ?>> int compare(T firstIndividual, T secondIndividual) {
        return secondIndividual.getFitness().compareTo(firstIndividual.getFitness());
    }

    private Stream<DetailedIndividual<Double, Individual<Box>>> toDetailedIndividuals(final Generation<Double, Individual<Box>> generation) {
        return generation.getRatedIndividuals()
                .stream()
                .map(ratedIndividual -> new DetailedIndividualBuilder<Double, Individual<Box>>()
                        .withRatedIndividual(ratedIndividual)
                        .withNumberOfGeneration(generation.getId())
                        .build()
                );
    }
}
