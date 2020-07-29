package genetic.generations.creator

import genetic.api.generation.GenerationContext
import genetic.api.individual.Individual
import genetic.api.individual.Organism
import genetic.api.individual.impl.RatedIndividual
import genetic.api.mutation.Mutator
import genetic.evaluator.elitism.ElitistPicker
import genetic.api.generation.Generation
import genetic.recombinators.Recombinator
import genetic.selectors.PairSelector

class OngoingGenerationCreator<T, P>( //todo feature toggle for cloning
    private val elitistPicker: ElitistPicker,
    private val pairSelector: PairSelector,
    private val singleChildRecombinator: Recombinator<Organism<P>, Individual<P>>,
    private val twoChildrenRecombinator: Recombinator<Organism<P>, Individual<P>>,
    private val mutator: Mutator<T, P>
) : (Generation<P>, GenerationContext<T>) -> Sequence<Individual<P>> {

  override fun invoke(
      generation: Generation<P>,
      evolutionContext: GenerationContext<T>
  ): Sequence<Individual<P>> {
    val elites = elitistPicker.pick(
        fitnessTesteds = generation.ratedIndividuals,
        count = evolutionContext.numberOfEliteIndividuals
    )
    val numberOfNewIndividualsIsOdd =
        evolutionContext.numberOfEliteIndividuals % 2 != 0

    val newIndividuals =
        pairSelector.select(generation.ratedIndividuals) { (generation.ratedIndividuals.count() - elites.count()) / 2 }
            .flatMap(twoChildrenRecombinator)
            .plus(createSingleForEven(
                fitnessTesteds = generation.ratedIndividuals,
                numberOfNewIndividualsIsOdd = numberOfNewIndividualsIsOdd
            )
                .onEach { mutator(it, evolutionContext.embryo) })
    return newIndividuals + elites
  }

  private fun createSingleForEven(
      fitnessTesteds: Sequence<RatedIndividual<P>>,
      numberOfNewIndividualsIsOdd: Boolean
  ) = if (numberOfNewIndividualsIsOdd) {
    pairSelector.select(fitnessTesteds) { 1 }
        .single()
        .let(singleChildRecombinator)
  } else emptySequence()
}