package genetic.packer.evolution.mutation.impl

import genetic.api.individual.Organism
import genetic.api.mutation.Mutator
import javafx.geometry.Bounds
import javafx.scene.shape.Box
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom

@Component
class MutatorImpl(
    @Qualifier("boundsAwareBoxTranslationRandomizer") private val translationRandomizer: (Box, Bounds) -> Unit,
    private val random: () -> ThreadLocalRandom
) : Mutator<Bounds, Box> {

  private val mutation = {
    random().nextDouble() <= mutationChance
  }

  override fun invoke(individual: Organism<Box>, bounds: Bounds) {
    individual.cells
        .map { it.nucleus }
        .forEach {
          if (mutation()) {
            translationRandomizer(it, bounds)
          }
        }
  }

  companion object {
    private const val mutationChance = 0.008
  }
}