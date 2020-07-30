package genetic.selectors.roulette

import genetic.api.individual.Rated
import genetic.selectors.PairSelector
import java.util.TreeMap
import java.util.concurrent.ThreadLocalRandom

class RoulettePairSelector<T : Rated>(
    private val treeMap: TreeMap<Double, T>,
    private val random: () -> Double
) : PairSelector<T> {

  override fun select() = generateSequence {
    Pair(
        first = selectSingle(),
        second = selectSingle()
    )
  }

  private fun selectSingle() = treeMap.ceilingEntry(random()).value
}