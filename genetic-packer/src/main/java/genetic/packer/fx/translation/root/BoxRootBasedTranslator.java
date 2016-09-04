package genetic.packer.fx.translation.root;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author piotr.larysz
 */
public abstract class BoxRootBasedTranslator implements BiConsumer<Box, Integer> {

    private BiConsumer<Box, Integer> translationSetter;

    private Function<Bounds, Double> boundsGetter;

    public BoxRootBasedTranslator(BiConsumer<Box, Integer> translationSetter, Function<Bounds, Double> boundsGetter) {
        this.translationSetter = translationSetter;
        this.boundsGetter = boundsGetter;
    }

    @Override
    public void accept(Box box, Integer position) {
        translationSetter.accept(box, createTranslation(box, position));
    }

    private int createTranslation(Box box, Integer position) {
        return (int) (position + boundsGetter.apply(box.getBoundsInParent()) / 2);
    }
}
