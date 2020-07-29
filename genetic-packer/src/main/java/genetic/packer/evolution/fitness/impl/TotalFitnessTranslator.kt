package genetic.packer.evolution.fitness.impl

import genetic.packer.evolution.fitness.FitnessComponents
import genetic.api.fitness.FitnessTranslator

class TotalFitnessTranslator : BaseFitnessTranslator(),
    FitnessTranslator<FitnessComponents, Double> {

  override fun apply(fitnessComponents: FitnessComponents) =
      rateIntersections(fitnessComponents.intersections) + 1 / (fitnessComponents.volume * 100)
}