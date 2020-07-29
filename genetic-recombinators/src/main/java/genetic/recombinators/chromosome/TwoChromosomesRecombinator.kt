package genetic.recombinators.chromosome

import genetic.api.individual.Cell
import java.util.concurrent.ThreadLocalRandom

class TwoChromosomesRecombinator<T>(
    random: () -> ThreadLocalRandom,
    cellCloner: (Cell<T>) -> Cell<T>
) : ChromosomeRecombinator<T>(random = random, cellCloner = cellCloner) {

  override fun recombine(
      first: Sequence<Cell<T>>,
      second: Sequence<Cell<T>>,
      exchangePoint: Int
  ): Sequence<Sequence<Cell<T>>> {
    val firstChildChromosome = first.take(exchangePoint) + second.drop(exchangePoint)
    val secondChildChromosome = second.take(exchangePoint) + first.drop(exchangePoint)
    return sequenceOf(firstChildChromosome, secondChildChromosome)
  }

  override fun resolveDefault(
      first: Sequence<Cell<T>>,
      second: Sequence<Cell<T>>
  ) = sequenceOf(first + second)
}