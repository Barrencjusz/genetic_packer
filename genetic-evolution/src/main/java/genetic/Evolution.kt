package genetic

import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.impl.DetailedIndividual
import genetic.api.statistics.GenerationStatistics
import genetic.statistics.GenerationStatisticsCreator
import org.slf4j.LoggerFactory
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Evolution<T, P>(
    private val generationsFactory: (GenerationContext<P>) -> () -> Generation<T>,
    private val contextMapper: ContextMapper<P>,
    private val bestIndividualsSelector: (Sequence<Generation<T>>) -> Sequence<DetailedIndividual<T>>,
    private val generationStatisticsCreator: GenerationStatisticsCreator
) {

  @OptIn(ExperimentalTime::class)
  operator fun invoke(context: Context<P>) = with(generationsFactory(contextMapper(context))) {
    var bestScore = 0.0
    generateSequence {
      val timedValue = measureTimedValue { this() }
      //      println(timedValue.duration)
      timedValue.value
    }.take(context.numberOfGenerations)
        .map { it.ratedIndividuals.maxBy { it.fitness.score }!! }
        .filter {
          (if (it.fitness.score > bestScore) {
            bestScore = it.fitness.score
            true
          } else false)
        }
    //        .let {
    //          Result(
    //              generations = it,
    //              topIndividuals = //bestIndividualsSelector(it).take(context.numberOfTopIndividuals),
    //              generationStats = generationStatisticsCreator.create(it)
    //          )
    //        }
  }

  data class Context<T>(
      val numberOfGenerations: Int,
      val generationSize: Int,
      val numberOfEliteIndividuals: Int,
      val embryo: T
  )

  data class Result<T>(
      val generations: Sequence<Generation<T>>,
      val topIndividuals: Sequence<DetailedIndividual<T>>,
      val generationStats: Sequence<GenerationStatistics>
  )

  interface ContextMapper<T> : (Context<T>) -> GenerationContext<T>

  companion object {
    private val LOGGER = LoggerFactory.getLogger(Evolution::class.java)
  }
}