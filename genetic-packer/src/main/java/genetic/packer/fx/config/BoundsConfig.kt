package genetic.packer.fx.config

import genetic.packer.fx.specification.BoundsGetter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BoundsConfig {

  @Bean
  fun xBoundsGetters() = Pair(BoundsGetter { it.minX }, BoundsGetter { it.maxX })

  @Bean
  fun yBoundsGetters() = Pair(BoundsGetter { it.minY }, BoundsGetter { it.maxY })

  @Bean
  fun zBoundsGetters() = Pair(BoundsGetter { it.minZ }, BoundsGetter { it.maxZ })

  @Bean
  fun boundGetters() = setOf(xBoundsGetters(), yBoundsGetters(), zBoundsGetters())

  @Bean
  fun xSizeGetter() = BoundsGetter { it.width }

  @Bean
  fun ySizeGetter() = BoundsGetter { it.height }

  @Bean
  fun zSizeGetter() = BoundsGetter { it.depth }
}