package genetic.packer.fx.config;

import java.util.function.BiConsumer;

import genetic.packer.fx.specification.BoxTranslator;
import javafx.scene.shape.Box;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author piotr.larysz
 */
@Configuration
public class TranslationConfig {

    @Bean
    public BoxTranslator xBoxTranslator() {
        return Box::setTranslateX;
    }

    @Bean
    public BoxTranslator yBoxTranslator() {
        return Box::setTranslateY;
    }

    @Bean
    public BoxTranslator zBoxTranslator() {
        return Box::setTranslateZ;
    }
}
