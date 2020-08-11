package genetic.statistics.best

import genetic.api.fitness.Fitness
import genetic.api.generation.Generation

class BestFitnessResolver : (Generation<*>) -> Fitness {

  override fun invoke(generation: Generation<*>) = generation.ratedIndividuals.maxBy {
    it.fitness.score
  }!!.fitness
}