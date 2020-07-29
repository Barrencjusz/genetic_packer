package genetic.packer.evolution.fitness.impl

import genetic.packer.evolution.fitness.FitnessComponents
import genetic.api.fitness.FitnessTranslator

class FitnessExplainedTranslator : BaseFitnessTranslator(),
    FitnessTranslator<FitnessComponents, String> {

  override fun apply(fitnessComponents: FitnessComponents) = String.format(
      TEXT,
      fitnessComponents.intersections.filter { it }.count(),
      rateIntersections(fitnessComponents.intersections),
      fitnessComponents.volume
  )

  companion object {
    private const val TEXT = "%d boxes intersects, intersection score: %.2f, volume: %.2f"
  }
}