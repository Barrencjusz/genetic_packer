package genetic.selectors.roulette.impl

import genetic.api.individual.Rated
import genetic.selectors.PairSelector
import genetic.selectors.roulette.RouletteDistancer
import java.util.TreeMap
import java.util.concurrent.ThreadLocalRandom

class RoulettePairSelector(
    private val rouletteDistancer: RouletteDistancer,
    private val random: () -> ThreadLocalRandom
) : PairSelector {

  override fun <M : Rated> select(
      fitnessTesteds: Sequence<M>, // FIXME factory indeed
      sizeFunction: (Int) -> Int
  ): Sequence<Pair<M, M>> {
    val fitnessSum = fitnessTesteds.sumByDouble { it.fitness.score }
    val treeMap = rouletteDistancer(fitnessTesteds) // FIXME this should be only once initialized
    return generateSequence {
      getPair(
          treeMap = treeMap,
          fitnessSum = fitnessSum
      )
    }.take(sizeFunction(fitnessTesteds.count()))
  }

  private fun <T> getPair(treeMap: TreeMap<Double, T>, fitnessSum: Double) =
      Pair(
          first = getSingle(
              treeMap = treeMap,
              fitnessSum = fitnessSum
          ),
          second = getSingle(
              treeMap = treeMap,
              fitnessSum = fitnessSum
          )
      )

  private fun <T> getSingle(treeMap: TreeMap<Double, T>, fitnessSum: Double) =
      treeMap.ceilingEntry(random().nextDouble(fitnessSum)).value
}