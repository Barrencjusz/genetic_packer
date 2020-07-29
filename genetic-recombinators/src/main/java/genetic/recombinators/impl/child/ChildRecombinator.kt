package genetic.recombinators.impl.child

import genetic.api.individual.Individual
import genetic.api.individual.Organism
import genetic.api.individual.impl.SimpleIndividual
import genetic.recombinators.Recombinator
import genetic.recombinators.chromosome.ChromosomeRecombinator

abstract class ChildRecombinator<T>(
    private val chromosomeRecombinator: ChromosomeRecombinator<T>
) : Recombinator<Organism<T>, Individual<T>> {

  override fun invoke(pair: Pair<Organism<T>, Organism<T>>) = pair.let { Pair(it.first.cells, it.second.cells) }
      .let(chromosomeRecombinator)
      .map { SimpleIndividual(it) }
}