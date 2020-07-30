package genetic.generations.creator

import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.Evaluated
import genetic.api.individual.Individual
import genetic.evaluator.EvaluatorImpl
import java.util.function.Supplier

class GenerationCreator<T, P>(
    private val context: GenerationContext<T>,
    private val firstGenerationCreator: (T, Int) -> Sequence<Individual<P>>,
    private val ongoingGenerationCreator: (Generation<P>, GenerationContext<T>) -> Sequence<Individual<P>>,
    private val idGenerator: () -> Int,
    private val evaluator: EvaluatorImpl
) : () -> Generation<P> {

  private lateinit var lastGeneration: Generation<P>

  private val ongoingStrategy = {
    ongoingGenerationCreator(lastGeneration, context)
  }

  private val firstTimeStrategy = {
    strategy = ongoingStrategy
    firstGenerationCreator(context.embryo, context.generationSize)
  }

  private var strategy: () -> Sequence<Individual<P>> = firstTimeStrategy

  override fun invoke() = strategy()
      .let { rateIndividuals(it) }
      .let {
        Generation(
            number = idGenerator(),
            ratedIndividuals = it.toList()
        )
      }
      .also { lastGeneration = it }

  private fun rateIndividuals(individuals: Sequence<Evaluated<P>>) =
      individuals.map { it.evaluate(evaluator) }
}