package genetic.recombinators.chromosome

import java.util.concurrent.ThreadLocalRandom

class SingleChromosomeRecombinator<T>(
    random: () -> ThreadLocalRandom
) : ChromosomeRecombinator<T>(random = random) {

  override fun recombine(
      first: Sequence<T>,
      second: Sequence<T>,
      exchangePoint: Int
  ) = sequenceOf(first.take(exchangePoint) + second.drop(exchangePoint))

  override fun resolveDefault(
      first: Sequence<T>,
      second: Sequence<T>
  ) = when (random().nextBoolean()) {
    true -> first
    false -> second
  }.let { sequenceOf(it) }
}