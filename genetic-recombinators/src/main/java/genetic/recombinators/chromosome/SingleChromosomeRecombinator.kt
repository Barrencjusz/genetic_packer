package genetic.recombinators.chromosome

import genetic.api.individual.Cell
import java.util.concurrent.ThreadLocalRandom

class SingleChromosomeRecombinator<T>(
    random: () -> ThreadLocalRandom,
    cellCloner: (Cell<T>) -> Cell<T>
) : ChromosomeRecombinator<T>(random = random, cellCloner = cellCloner) {

  override fun recombine(
      first: Sequence<Cell<T>>,
      second: Sequence<Cell<T>>,
      exchangePoint: Int
  ) = sequenceOf(first.take(exchangePoint) + second.drop(exchangePoint))

  override fun resolveDefault(
      first: Sequence<Cell<T>>,
      second: Sequence<Cell<T>>
  ) = when (random().nextBoolean()) {
    true -> first
    false -> second
  }.let { sequenceOf(it) }
}