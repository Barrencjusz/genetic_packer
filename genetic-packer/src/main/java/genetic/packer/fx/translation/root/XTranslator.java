package genetic.packer.fx.translation.root;

import genetic.packer.fx.translation.root.BoxRootBasedTranslator;
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
@Component("xTranslator")
public class XTranslator extends BoxRootBasedTranslator {

    @Autowired
    public XTranslator(@Qualifier("xTranslationSetter") BiConsumer<Box, Integer> setter, Function<Bounds, Double> xBoundsGetter) {
        super(setter, xBoundsGetter);
    }
}