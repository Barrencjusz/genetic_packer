package genetic.packer.evolution.fitness

import genetic.api.fitness.FitnessTranslator
import genetic.api.fitness.TranslatedFitness

class FitnessComponents(
    totalFitnessTranslator: FitnessTranslator<FitnessComponents, Double>,
    fitnessExplainedTranslator: FitnessTranslator<FitnessComponents, String>,
    val intersections: Sequence<Boolean>,
    val volume: Double
) : TranslatedFitness<FitnessComponents>(totalFitnessTranslator, fitnessExplainedTranslator) {

  override fun self() = this
}