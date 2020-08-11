package genetic.recombinators.chromosome

import java.util.concurrent.ThreadLocalRandom

class TwoChromosomesRecombinator<T, U>(
    random: () -> ThreadLocalRandom
) : ChromosomeRecombinator<T, U>(random = random) {

  override fun recombine(
      first: Sequence<T>,
      second: Sequence<T>,
      exchangePoint: Int
  ): Sequence<Sequence<T>> {
    val firstChildChromosome = first.take(exchangePoint) + second.drop(exchangePoint)
    val secondChildChromosome = second.take(exchangePoint) + first.drop(exchangePoint)
    return sequenceOf(firstChildChromosome, secondChildChromosome)
  }

  override fun resolveDefault(
      first: Sequence<T>,
      second: Sequence<T>
  ) = sequenceOf(first, second)
}