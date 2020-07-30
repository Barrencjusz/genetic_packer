package genetic.statistics

import genetic.api.generation.Generation
import genetic.api.individual.impl.DetailedIndividual

class BestIndividualsSelector<T> : (Sequence<Generation<T>>) -> Sequence<DetailedIndividual<T>> {

  override fun invoke(generations: Sequence<Generation<T>>) = generations
      .flatMap { toDetailedIndividuals(it) }
      .sortedBy { it.fitness.score }

  private fun toDetailedIndividuals(generation: Generation<T>) = generation.ratedIndividuals
      .map {
        DetailedIndividual(
            ratedIndividual = it,
            numberOfGeneration = generation.number
        )
      }
      .asSequence()
}