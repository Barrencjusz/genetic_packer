package genetic.selectors

import genetic.api.individual.Rated

interface PairSelector<T : Rated> {

  fun select(): Sequence<Pair<T, T>>

  interface Factory<T : Rated> : (Iterable<T>) -> PairSelector<T>
}