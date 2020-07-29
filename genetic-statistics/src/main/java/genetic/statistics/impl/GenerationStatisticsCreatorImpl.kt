package genetic.statistics.impl

import genetic.api.fitness.Fitness
import genetic.api.generation.Generation
import genetic.api.statistics.GenerationStatistics
import genetic.statistics.GenerationStatisticsCreator

class GenerationStatisticsCreatorImpl(
    private val bestFitnessResolver: (Generation<*>) -> Fitness,
    private val averageFitnessResolver: (Generation<*>) -> Double
) : GenerationStatisticsCreator {

  override fun create(generations: Sequence<Generation<*>>) = generations.map {
    createGenerationStatistics(it)
  }

  private fun createGenerationStatistics(generation: Generation<*>): GenerationStatistics {
    return bestFitnessResolver(generation).let {
      GenerationStatistics(
          bestFitness = it.score,
          averageFitness = averageFitnessResolver(generation),
          bestFitnessExplained = it.explain(),
          averageNaturalFitness = 0.0, //FIXME
          generationNumber = generation.number
      )
    }
  }
}