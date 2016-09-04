package genetic.packer.fx.translation;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author piotr.larysz
 */
@Component
public class BoundsAwareBoxTranslationRandomizer implements BiConsumer<Box, Bounds> {

    @Autowired
    private Supplier<ThreadLocalRandom> random;

    @Autowired
    private BiConsumer<Box, Integer> xTranslator;

    @Autowired
    private BiConsumer<Box, Integer> yTranslator;

    @Autowired
    private BiConsumer<Box, Integer> zTranslator;

    @Autowired
    private BiFunction<Double, Double, Integer> maxBoundsCalculator;

    @Override
    //TODO refactor - method references
    public void accept(Box box, Bounds bounds) {

        final int xPosition = random.get().nextInt(maxBoundsCalculator.apply(bounds.getMaxX(), box.getWidth()));
        final int yPosition = random.get().nextInt(maxBoundsCalculator.apply(bounds.getMaxY(), box.getHeight()));
        final int zPosition = random.get().nextInt(maxBoundsCalculator.apply(bounds.getMaxZ(), box.getDepth()));

        xTranslator.accept(box, xPosition);
        yTranslator.accept(box, yPosition);
        zTranslator.accept(box, zPosition);
    }

}