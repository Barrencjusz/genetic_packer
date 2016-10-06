package genetic.packer.evolution.generation;

import genetic.packer.evolution.generation.dto.IndividualBuilder;
import genetic.packer.fx.Cell;
import genetic.packer.fx.CellBuilder;
import genetic.packer.fx.copy.BoxSizeCloner;
import genetic.packer.evolution.generation.dto.Embryo;
import genetic.packer.evolution.generation.dto.Individual;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class IndividualCreator implements Function<Embryo, Supplier<Individual<Box>>> {

    @Autowired
    private BoxSizeCloner boxSizeCloner;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Autowired
    @Qualifier("boundsAwareBoxTranslationRandomizer")
    private BiConsumer<Box, Bounds> translationRandomizer;

    @Override
    public Supplier<Individual<Box>> apply(Embryo embryo) {
        AtomicInteger integer = new AtomicInteger();
        return () ->
            this.boxSizeCloner.clone(embryo.getBoxes()).stream()
                .peek(this.randomizeAround(embryo.getBounds()))
                .map(this.createCell(integer.getAndIncrement()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), cells -> new IndividualBuilder<Box>().withCells(cells).build()));
    }

    private Consumer<Box> randomizeAround(Bounds bounds) {
        return box -> this.translationRandomizer.accept(box, bounds);
    }

    private Function<Box, Cell<Box>> createCell(int order) {
        return box -> new CellBuilder<Box>()
            .withNucleus(box)
            .withOrder(order)
            .withProcessingOrder(this.random.get().nextInt())
            .build();
    }
}
