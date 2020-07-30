package genetic.packer.evolution.fitness.impl

import genetic.api.fitness.FitnessTranslator
import genetic.packer.evolution.fitness.FitnessComponents

class TotalFitnessTranslator : BaseFitnessTranslator(),
    FitnessTranslator<FitnessComponents, Double> {

  override fun apply(fitnessComponents: FitnessComponents) =
      rateIntersections(fitnessComponents.intersections) + 1 / (fitnessComponents.volume * 100)
}