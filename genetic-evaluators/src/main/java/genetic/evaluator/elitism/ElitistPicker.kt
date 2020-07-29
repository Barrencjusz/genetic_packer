package genetic.evaluator.elitism

import genetic.api.individual.Individual
import genetic.api.individual.impl.RatedIndividual

interface ElitistPicker {

  fun <T> pick(
      fitnessTesteds: Sequence<RatedIndividual<T>>,
      count: Int
  ): Sequence<Individual<T>>
}