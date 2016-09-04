package genetic.packer.config;

import com.google.common.collect.ImmutableList;
import javafx.scene.shape.Box;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author piotr.larysz
 */
@Configuration
public class BoxTranslationListConfig {

    @Bean
    public List<BiConsumer<Box, Double>> boxTranslationList() {
        return ImmutableList.<BiConsumer<Box, Double>> builder()
                .add(Box::setHeight)
                .add(Box::setWidth)
                .add(Box::setDepth)
                .build();
    }
}
