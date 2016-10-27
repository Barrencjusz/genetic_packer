package genetic.packer.evolution.generation;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import genetic.api.individual.Cell;
import genetic.api.individual.CellBuilder;
import genetic.api.individual.Individual;
import genetic.api.individual.impl.SimpleIndividual;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.fx.copy.BoxSizeCloner;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import javaslang.collection.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class IndividualCreator implements Function<Embryo, Supplier<Individual<Double, Box>>> {

    @Autowired
    private BoxSizeCloner boxSizeCloner;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Autowired
    @Qualifier("boundsAwareBoxTranslationRandomizer")
    private BiConsumer<Box, Bounds> translationRandomizer;

    @Override
    public Supplier<Individual<Double, Box>> apply(Embryo embryo) {
        AtomicInteger integer = new AtomicInteger();
        return () -> embryo.getBoxes()
            .map(this.boxSizeCloner::clone)
            .peek(this.randomizeAround(embryo.getBounds()))
            .map(this.createCell(integer.getAndIncrement()))
            .toStream()
            .transform(getIndividual());
    }

    private Function<Stream<Cell<Box>>, Individual<Double, Box>> getIndividual() {
        return SimpleIndividual::new;
    }

    private Consumer<Box> randomizeAround(Bounds bounds) {
        return box -> this.translationRandomizer.accept(box, bounds);
    }

    private Function<Box, Cell<Box>> createCell(int order) {
        return box -> new CellBuilder<Box>()
            .nucleus(box)
            .order(order)
            .processingOrder(this.random.get().nextInt())
            .build();
    }
}
