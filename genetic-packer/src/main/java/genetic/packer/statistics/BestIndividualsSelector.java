package genetic.packer.statistics;

import java.util.function.BiFunction;

import genetic.api.individual.impl.DetailedIndividual;
import genetic.api.individual.impl.DetailedIndividualBuilder;
import genetic.api.individual.impl.RatedIndividual;
import genetic.packer.evolution.generation.dto.Generation;
import javafx.scene.shape.Box;
import javaslang.collection.Seq;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Mapper
@Component//FIXME too long bifunction declaration
public abstract class BestIndividualsSelector implements BiFunction<Seq<Generation<Double, Box>>, Integer, Seq<DetailedIndividual<Double, Box>>> {

    @Override
    public Seq<DetailedIndividual<Double, Box>> apply(Seq<Generation<Double, Box>> generations, Integer size) {
        return generations
            .flatMap(this::toDetailedIndividuals)
            .sorted(this::compare)
            .take(size);
    }

    private <T extends RatedIndividual<Double, ?>> int compare(T firstIndividual, T secondIndividual) {
        return secondIndividual.getFitness().compareTo(firstIndividual.getFitness());
    }

    private Seq<DetailedIndividual<Double, Box>> toDetailedIndividuals(final Generation<Double, Box> generation) {
        return generation.getRatedIndividuals()
                .map(ratedIndividual -> new DetailedIndividualBuilder<Double, Box>()
                        .ratedIndividual(ratedIndividual)
                        .numberOfGeneration(generation.getId())
                        .build()
                ).toList();
    }
}
