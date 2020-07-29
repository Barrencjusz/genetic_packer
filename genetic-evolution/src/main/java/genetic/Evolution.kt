package genetic

import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.impl.DetailedIndividual
import genetic.api.statistics.GenerationStatistics
import genetic.statistics.GenerationStatisticsCreator
import org.slf4j.LoggerFactory

class Evolution<T, P>(
    private val generationsFactory: (GenerationContext<P>) -> () -> Generation<T>,
    private val contextMapper: (Context<P>) -> GenerationContext<P>,
    private val bestIndividualsSelector: (Sequence<Generation<T>>, Int) -> Sequence<DetailedIndividual<T>>,
    private val generationStatisticsCreator: GenerationStatisticsCreator
) : (Evolution.Context<P>) -> Evolution.Result<T> {

  override fun invoke(context: Context<P>) = with(generationsFactory(contextMapper(context))) {
    generateSequence { this() }
        .take(context.numberOfGenerations)
        .let {
          Result(
              generations = it,
              topIndividuals = bestIndividualsSelector(it, context.numberOfTopIndividuals),
              generationStats = generationStatisticsCreator.create(it)
          )
        }
  }

  data class Context<T>(
      val numberOfGenerations: Int,
      val generationSize: Int,
      val embryo: T,
      val numberOfTopIndividuals: Int,
      val numberOfEliteIndividuals: Int
  )

  data class Result<T>(
      val generations: Sequence<Generation<T>>,
      val topIndividuals: Sequence<DetailedIndividual<T>>,
      val generationStats: Sequence<GenerationStatistics>
  )

  companion object {
    private val LOGGER = LoggerFactory.getLogger(Evolution::class.java)
  }
}