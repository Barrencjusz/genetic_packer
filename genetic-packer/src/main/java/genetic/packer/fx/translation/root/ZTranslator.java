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
@Component("zTranslator")
public class ZTranslator extends BoxRootBasedTranslator {

    @Autowired
    public ZTranslator(@Qualifier("zTranslationSetter") BiConsumer<Box, Double> zTranslationSetter, Function<Bounds, Double> zBoundsGetter) {
        super(zTranslationSetter, zBoundsGetter);
    }
}
