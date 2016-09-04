package genetic.packer.stats;

import genetic.packer.generation.dto.DetailedIndividual;
import genetic.packer.generation.dto.DetailedIndividualBuilder;
import genetic.packer.generation.dto.Generation;
import genetic.packer.generation.dto.Individual;
import genetic.selectors.dto.RatedIndividual;
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
public abstract class BestIndividualsSelector implements BiFunction<List<Generation<Double, Individual>>, Integer, List<DetailedIndividual<Double, Individual>>> {

    @Override
    public List<DetailedIndividual<Double, Individual>> apply(List<Generation<Double, Individual>> generations, Integer size) {
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

    private Stream<DetailedIndividual<Double, Individual>> toDetailedIndividuals(final Generation<Double, Individual> generation) {
        return generation.getRatedIndividuals()
                .stream()
                .map(ratedIndividual -> new DetailedIndividualBuilder<Double, Individual>()
                        .withRatedIndividual(ratedIndividual)
                        .withNumberOfGeneration(generation.getId())
                        .build()
                );
    }
}
