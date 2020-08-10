package genetic.generations.creator

import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.Individual
import genetic.evaluator.EvaluatorImpl

class GenerationCreator<T, P>(
    private val context: GenerationContext<T>,
    private val firstGenerationCreator: (T, Int) -> Sequence<Individual<P>>,
    private val ongoingGenerationCreator: (Generation<P>, GenerationContext<T>) -> Sequence<Individual<P>>,
    private val idGenerator: () -> Int,
    private val evaluator: EvaluatorImpl<P>
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
      .map { it.evaluate(evaluator) }
      .let {
        Generation(
            number = idGenerator(),
            ratedIndividuals = it.toList()
        )
      }
      .also { lastGeneration = it }

}