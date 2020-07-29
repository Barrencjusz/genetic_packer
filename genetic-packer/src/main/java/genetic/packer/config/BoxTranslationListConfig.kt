package genetic.packer.config

import javafx.scene.shape.Box
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.BiConsumer

@Configuration
class BoxTranslationListConfig {

  @Bean
  fun boxTranslationList() = listOf<(Box, Double) -> Unit>(
      { obj, v -> obj.height = v },
      { obj, v -> obj.width = v },
      { obj, v -> obj.depth = v }
  )
}