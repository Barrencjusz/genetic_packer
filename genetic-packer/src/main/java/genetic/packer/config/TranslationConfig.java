package genetic.packer.config;

import javafx.geometry.Bounds;
import javafx.scene.shape.Box;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author piotr.larysz
 */
@Configuration
public class TranslationConfig {

    @Bean
    public BiConsumer<Box, Double> xTranslationSetter() {
        return Box::setTranslateX;
    }

    @Bean
    public BiConsumer<Box, Double> yTranslationSetter() {
        return Box::setTranslateY;
    }

    @Bean
    public BiConsumer<Box, Double> zTranslationSetter() {
        return Box::setTranslateZ;
    }

    @Bean
    public Function<Bounds, Double> xBoundsGetter() {
        return Bounds::getWidth;
    }

    @Bean
    public Function<Bounds, Double> yBoundsGetter() {
        return Bounds::getHeight;
    }

    @Bean
    public Function<Bounds, Double> zBoundsGetter() {
        return Bounds::getDepth;
    }
}
