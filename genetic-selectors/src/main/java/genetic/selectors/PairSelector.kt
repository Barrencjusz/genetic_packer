package genetic.selectors

import genetic.api.individual.Rated

interface PairSelector {

  fun <M : Rated> select(
      fitnessTesteds: Sequence<M>,
      sizeFunction: (Int) -> Int
  ): Sequence<Pair<M, M>>
}