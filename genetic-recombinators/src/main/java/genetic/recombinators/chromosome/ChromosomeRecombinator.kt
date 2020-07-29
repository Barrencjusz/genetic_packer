package genetic.recombinators.chromosome

import genetic.api.individual.Cell
import genetic.recombinators.Recombinator
import java.util.concurrent.ThreadLocalRandom

abstract class ChromosomeRecombinator<T1>(
    protected val random: () -> ThreadLocalRandom,
    private val cellCloner: (Cell<T1>) -> Cell<T1>
) : Recombinator<Sequence<Cell<T1>>, Sequence<Cell<T1>>> {

  private val recombination: () -> Boolean = { random().nextDouble() <= 0.7 }

  override fun invoke(pair: Pair<Sequence<Cell<T1>>, Sequence<Cell<T1>>>) =
      pair.let { (first, second) ->
        if (recombination()) {
          recombine(
              first = first,
              second = second,
              exchangePoint = random().nextInt(first.count())
          )
        } else resolveDefault(first, second)
      }
          .map { it.map(cellCloner) }

  protected abstract fun recombine(
      first: Sequence<Cell<T1>>,
      second: Sequence<Cell<T1>>,
      exchangePoint: Int
  ): Sequence<Sequence<Cell<T1>>>

  protected abstract fun resolveDefault(
      first: Sequence<Cell<T1>>,
      second: Sequence<Cell<T1>>
  ): Sequence<Sequence<Cell<T1>>>
}