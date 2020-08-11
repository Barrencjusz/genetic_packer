package genetic.packer.fx.translation.root;

import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;

public abstract class BoxRootBasedTranslator implements BiConsumer<Box, Integer> {

    private BiConsumer<Box, Double> translationSetter;

    private Function<Bounds, Double> sizeGetter;

    public BoxRootBasedTranslator(BiConsumer<Box, Double> translationSetter, Function<Bounds, Double> sizeGetter) {
        this.translationSetter = translationSetter;
        this.sizeGetter = sizeGetter;
    }

    @Override
    public void accept(Box box, Integer position) {
        translationSetter.accept(box, createTranslation(box, position));
    }

    private double createTranslation(Box box, Integer position) {
        return position + sizeGetter.apply(box.getBoundsInParent()) / 2;
    }
}
