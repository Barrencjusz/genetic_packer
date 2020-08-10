package genetic.recombinators.impl.child

import genetic.api.individual.Individual
import genetic.api.individual.Organism
import genetic.api.individual.impl.SimpleIndividual
import genetic.recombinators.Recombinator
import genetic.recombinators.chromosome.ChromosomeRecombinator

abstract class ChildRecombinator<T>(
    private val chromosomeRecombinator: ChromosomeRecombinator<T>
) : Recombinator<Organism<Sequence<T>>, Individual<Sequence<T>>> { // FIXME it should be more general

  override fun invoke(pair: Pair<Organism<Sequence<T>>, Organism<Sequence<T>>>) = pair.let { Pair(it.first.body, it.second.body) }
      .let(chromosomeRecombinator)
      .map { SimpleIndividual(it) }
}