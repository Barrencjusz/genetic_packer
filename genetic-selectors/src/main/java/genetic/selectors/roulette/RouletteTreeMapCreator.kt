package genetic.selectors.roulette

import genetic.api.individual.Rated
import java.util.TreeMap

class RouletteTreeMapCreator(
    private val keyCreatorFactory: () -> KeyCreator
) {

  operator fun <M : Rated> invoke(entries: Iterable<M>) =
      with(keyCreatorFactory()) {
        entries.associateByTo(TreeMap(), { this(it) })
      }

  class KeyCreator {

    private var currentKeyDistance = 0.0

    operator fun <M : Rated> invoke(rated: M) =
        rated.fitness.score.let { currentKeyDistance += it; currentKeyDistance }

    class Factory : () -> KeyCreator {

      override fun invoke() = KeyCreator()
    }
  }
}