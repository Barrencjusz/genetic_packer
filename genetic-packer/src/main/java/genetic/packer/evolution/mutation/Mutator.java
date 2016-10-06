package genetic.packer.evolution.mutation;

import genetic.packer.evolution.generation.dto.Individual;
import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
public class Mutator implements Consumer<Individual<Box>> {

    private static final double mutationChance = 0.01;

    @Autowired
    @Qualifier("boundsAwareBoxTranslationRandomizer")
    private BiConsumer<Box, Bounds> translationRandomizer;

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Resource
    private List<BiConsumer<Box, Double>> boxTranslationList;

    @Override
    public void accept(Individual individual) {
        //boxTranslationList.stream().forEach(regenerateForBounds);
    }
}
