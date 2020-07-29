package genetic.statistics

import genetic.api.generation.Generation
import genetic.api.individual.impl.DetailedIndividual

abstract class BestIndividualsSelector :
    (Sequence<Generation<*>>, Int) -> Sequence<DetailedIndividual<*>> {

  override fun invoke(
      generations: Sequence<Generation<*>>,
      size: Int
  ) = generations
      .flatMap { toDetailedIndividuals(it) }
      .sortedBy { it.fitness.score }
      .take(size)

  private fun toDetailedIndividuals(generation: Generation<*>) =
      generation.ratedIndividuals
          .map {
            DetailedIndividual(
                ratedIndividual = it,
                numberOfGeneration = generation.number
            )
          }
}