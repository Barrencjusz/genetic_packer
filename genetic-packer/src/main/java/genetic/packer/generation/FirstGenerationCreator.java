package genetic.packer.generation;

import genetic.packer.generation.dto.Embryo;
import genetic.packer.generation.dto.Generation;
import genetic.packer.generation.dto.GenerationBuilder;
import genetic.packer.generation.dto.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author piotr.larysz
 */
@Component
public class FirstGenerationCreator implements BiFunction<Embryo, Integer, Collection<Individual>> {

    @Autowired
    private Function<Embryo, Supplier<Individual>> individualCreator;

    @Override
    public Collection<Individual> apply(Embryo embryo, Integer size) {
        return Stream.generate(individualCreator.apply(embryo)).limit(size).collect(Collectors.toList());
    }

}