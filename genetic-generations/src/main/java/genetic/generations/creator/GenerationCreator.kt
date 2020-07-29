package genetic.generations.creator

import genetic.api.generation.GenerationContext
import genetic.api.individual.Evaluated
import genetic.api.individual.Individual
import genetic.evaluator.EvaluatorImpl
import genetic.api.generation.Generation
import java.util.function.Supplier

class GenerationCreator<T, P>(
    private val context: GenerationContext<T>,
    private val firstGenerationCreator: (T, Int) -> Sequence<Individual<P>>,
    private val ongoingGenerationCreator: (Generation<P>, GenerationContext<T>) -> Sequence<Individual<P>>,
    private val idGenerator: Supplier<Int>,
    private val evaluator: EvaluatorImpl
) : () -> Generation<P> {

  private val subsequentGenerations: MutableList<Generation<P>> = mutableListOf()

  private val ongoingStrategy = {
    ongoingGenerationCreator(
        subsequentGenerations.last(), context
    )
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
            number = idGenerator.get(),
            ratedIndividuals = it
        )
      }
      .also { subsequentGenerations.add(it) }

  private fun rateIndividuals(individuals: Sequence<Evaluated<P>>) =
      individuals.map { it.evaluate(evaluator) }
}