package genetic.selectors.roulette

import genetic.api.individual.Rated
import java.util.TreeMap

interface RouletteDistancer {

  operator fun <M : Rated> invoke(entries: Sequence<M>): TreeMap<Double, M>

  interface KeyCreator {

    operator fun <M : Rated> invoke(): (M) -> Double
  }
}