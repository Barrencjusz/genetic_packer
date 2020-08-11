package genetic.selectors.roulette

import genetic.api.individual.Rated
import genetic.selectors.PairSelector
import java.util.concurrent.ThreadLocalRandom

class RoulettePairSelectorFactory<T : Rated>(
    private val rouletteTreeMapCreator: RouletteTreeMapCreator,
    private val random: () -> ThreadLocalRandom
) : PairSelector.Factory<T> {

  override fun invoke(rateds: Iterable<T>): PairSelector<T> {

    val fitnessSum = rateds.sumByDouble { it.fitness.score }

    return RoulettePairSelector(
        treeMap = rouletteTreeMapCreator(rateds),
        random = { random().nextDouble(fitnessSum) }
    )
  }
}