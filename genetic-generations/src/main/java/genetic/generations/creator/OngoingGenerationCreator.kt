package genetic.generations.creator

import genetic.api.generation.Generation
import genetic.api.generation.GenerationContext
import genetic.api.individual.Individual
import genetic.api.individual.Organism
import genetic.api.individual.impl.RatedIndividual
import genetic.api.mutation.Mutator
import genetic.evaluator.elitism.ElitistPicker
import genetic.recombinators.Recombinator
import genetic.selectors.PairSelector

class OngoingGenerationCreator<T, P>( //todo feature toggle for cloning????
    private val elitistPicker: ElitistPicker,
    private val pairSelectorFactory: PairSelector.Factory<RatedIndividual<P>>,
    private val singleChildRecombinator: Recombinator<Organism<P>, Individual<P>>,
    private val twoChildrenRecombinator: Recombinator<Organism<P>, Individual<P>>,
    private val mutator: Mutator<T, P>
) : (Generation<P>, GenerationContext<T>) -> Sequence<Individual<P>> {

  override fun invoke(
      generation: Generation<P>,
      evolutionContext: GenerationContext<T>
  ): Sequence<Individual<P>> {
    val elites = elitistPicker.pick(fitnessTesteds = generation.ratedIndividuals)
        .take(evolutionContext.numberOfEliteIndividuals)

    val pairSelector = pairSelectorFactory(generation.ratedIndividuals)
    return pairSelector.select()
        .take((generation.ratedIndividuals.count() - elites.count()) / 2)
        .toList()
        .asSequence()
        .flatMap(twoChildrenRecombinator)
        .plus(
            createSingleForEven(
                pairSelector = pairSelector,
                numberOfNewIndividualsIsOdd = evolutionContext.numberOfEliteIndividuals % 2 != 0
            )
                .onEach { mutator(it, evolutionContext.embryo) }) + elites
  }

  private fun createSingleForEven(
      pairSelector: PairSelector<RatedIndividual<P>>,
      numberOfNewIndividualsIsOdd: Boolean
  ) = if (numberOfNewIndividualsIsOdd) {
    pairSelector.select()
        .take(1)
        .single()
        .let(singleChildRecombinator)
  } else emptySequence()
}