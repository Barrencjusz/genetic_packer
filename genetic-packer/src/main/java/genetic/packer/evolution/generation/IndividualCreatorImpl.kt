package genetic.packer.evolution.generation

import genetic.api.individual.Cell
import genetic.api.individual.Individual
import genetic.api.individual.IndividualCreator
import genetic.api.individual.impl.SimpleIndividual
import genetic.packer.evolution.generation.dto.Embryo
import genetic.packer.fx.copy.BoxSizeCloner
import javafx.geometry.Bounds
import javafx.scene.shape.Box
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.BiConsumer

@Component
class IndividualCreatorImpl(
    private val boxSizeCloner: BoxSizeCloner,
    private val random: () -> ThreadLocalRandom,
    @Qualifier("boundsAwareBoxTranslationRandomizer") private val translationRandomizer: BiConsumer<Box, Bounds>
) : IndividualCreator<Embryo, Box> {

  override fun invoke(embryo: Embryo): () -> Individual<Box> {
    val order = AtomicInteger() // FIXME is it absolutely required to have order the sequence will define order
    return {
      embryo.boxes
          .map { boxSizeCloner.clone(it) }
          .onEach {
            translationRandomizer.accept(
                it,
                embryo.bounds
            )
          }
          .map { Cell(order = order.getAndIncrement(), processingOrder = random().nextInt(), nucleus = it) }
          .let { SimpleIndividual(it) }
    }
  }
}