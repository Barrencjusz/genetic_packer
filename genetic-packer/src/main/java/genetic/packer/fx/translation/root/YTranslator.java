package genetic.packer.fx.translation.root;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@Component("yTranslator")
public class YTranslator extends BoxRootBasedTranslator {

    @Autowired
    public YTranslator(@Qualifier("yTranslationSetter") BiConsumer<Box, Double> yTranslationSetter, Function<Bounds, Double> yBoundsGetter) {
        super(yTranslationSetter, yBoundsGetter);
    }
}
