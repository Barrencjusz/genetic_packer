package genetic.packer.evolution.normalisation

import genetic.api.individual.Individual
import javafx.geometry.Bounds
import javafx.scene.shape.Box

class Normalizer : (Individual<Box>, Bounds) -> Individual<Box> {

  override fun invoke(
      individual: Individual<Box>,
      bounds: Bounds
  ): Individual<Box> {
    //fixme generate numbers at the beginning
    val order = intArrayOf(0) //fixme
    individual.cells
        .map { it.nucleus }
        .map { OrderedBox(order[0]++, it) }
    return individual //todo
  }

  private data class OrderedBox(val order: Int, val box: Box)
}