package genetic.packer.evolution.generation.creator;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.api.individual.Individual;
import genetic.packer.evolution.generation.dto.Embryo;
import javafx.scene.shape.Box;
import javaslang.collection.Stream;
import javaslang.collection.Traversable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class FirstGenerationCreator implements BiFunction<Embryo, Integer, Traversable<Individual<Double, Box>>> {

    @Autowired
    private Function<Embryo, Supplier<Individual<Double, Box>>> individualCreator;

    @Override
    public Traversable<Individual<Double, Box>> apply(Embryo embryo, Integer size) {
        return Stream.continually(individualCreator.apply(embryo)).take(size);
    }
}