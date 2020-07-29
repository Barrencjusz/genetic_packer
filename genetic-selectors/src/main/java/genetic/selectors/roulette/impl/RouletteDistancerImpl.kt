package genetic.selectors.roulette.impl

import genetic.api.individual.Rated
import genetic.selectors.roulette.RouletteDistancer
import genetic.selectors.roulette.RouletteDistancer.KeyCreator
import java.util.TreeMap

class RouletteDistancerImpl(
    private val distancedKeyCreator: KeyCreator
) : RouletteDistancer {

  override fun <M : Rated> invoke(entries: Sequence<M>) =
      entries.associateByTo(TreeMap(), distancedKeyCreator())

  class DistancedKeyCreator : KeyCreator {

    private var currentKeyDistance = 0.0

    override fun <M : Rated> invoke() = { fitnessTested: M ->
      fitnessTested.fitness.score.let { currentKeyDistance += it; currentKeyDistance }
    }
  }
}