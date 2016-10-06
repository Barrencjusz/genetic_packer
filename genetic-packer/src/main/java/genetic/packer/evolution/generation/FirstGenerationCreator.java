package genetic.packer.evolution.generation;

import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Individual;
import javafx.scene.shape.Box;
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
public class FirstGenerationCreator implements BiFunction<Embryo, Integer, Collection<Individual<Box>>> {

    @Autowired
    private Function<Embryo, Supplier<Individual<Box>>> individualCreator;

    @Override
    public Collection<Individual<Box>> apply(Embryo embryo, Integer size) {
        return Stream.generate(individualCreator.apply(embryo))
            .limit(size)
            .collect(Collectors.toList());
    }
}