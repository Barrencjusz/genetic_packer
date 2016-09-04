package genetic.packer.generation;

import genetic.packer.fx.copy.BoxSizeCloner;
import genetic.packer.generation.dto.Embryo;
import genetic.packer.generation.dto.Individual;
import genetic.packer.generation.dto.IndividualBuilder;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author piotr.larysz
 */
@Component
public class IndividualCreator implements Function<Embryo, Supplier<Individual>> {

    @Autowired
    private Consumer<Box> containerCentralizer;

    @Autowired
    @Qualifier("boundsAwareBoxTranslationRandomizer")
    private BiConsumer<Box, Bounds> translationRandomizer;

    @Autowired
    private BoxSizeCloner boxSizeCopier;

    @Override
    public Supplier<Individual> apply(Embryo embryo) {
        return () ->
            boxSizeCopier.clone(embryo.getBoxes()).stream()
                    .peek(randomizeAround(embryo.getBounds()))
                    .collect(Collectors.collectingAndThen(Collectors.toList(), boxes -> new IndividualBuilder().withBoxes(boxes).build()));
    }

    private Consumer<Box> randomizeAround(Bounds bounds) {
        return box -> translationRandomizer.accept(box, bounds);
    }
}
