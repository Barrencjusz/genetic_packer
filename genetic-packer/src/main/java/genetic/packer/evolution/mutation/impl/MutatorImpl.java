package genetic.packer.evolution.mutation.impl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import genetic.packer.evolution.generation.dto.individual.impl.SimpleIndividual;
import genetic.packer.evolution.mutation.Mutator;
import genetic.packer.evolution.generation.dto.Cell;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author piotr.larysz
 */
@Component
public class MutatorImpl implements Mutator {

    private static final double mutationChance = 0.008;

    @Autowired
    @Qualifier("boundsAwareBoxTranslationRandomizer")
    private BiConsumer<Box, Bounds> translationRandomizer;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    private final Supplier<Boolean> mutation = () -> random.get().nextDouble() <= mutationChance; //todo make some component holding the random also


    @Override
    public void accept(SimpleIndividual<Box> individual, Bounds bounds) {
        individual.getCells().stream()
            .map(Cell::getNucleus)
            .forEach(box -> {
                if(mutation.get()) {
                    this.translationRandomizer.accept(box, bounds);
                }
            });
    }
}
