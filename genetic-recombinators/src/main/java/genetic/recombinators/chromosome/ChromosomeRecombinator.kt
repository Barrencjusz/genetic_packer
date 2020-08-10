package genetic.recombinators.chromosome

import genetic.recombinators.Recombinator
import java.util.concurrent.ThreadLocalRandom

abstract class ChromosomeRecombinator<T>(
    protected val random: () -> ThreadLocalRandom
) : Recombinator<Sequence<T>, Sequence<T>> {

  private val recombination: () -> Boolean = { random().nextDouble() <= 0.7 }

  override fun invoke(pair: Pair<Sequence<T>, Sequence<T>>) =
      pair.let { (first, second) ->
        if (recombination()) {
          recombine(
              first = first,
              second = second,
              exchangePoint = random().nextInt(first.count() + 1)
          )
        } else resolveDefault(first, second)
      }

  protected abstract fun recombine(
      first: Sequence<T>,
      second: Sequence<T>,
      exchangePoint: Int
  ): Sequence<Sequence<T>>

  protected abstract fun resolveDefault(
      first: Sequence<T>,
      second: Sequence<T>
  ): Sequence<Sequence<T>>
}