package genetic.evaluator.elitism

import genetic.api.individual.Individual
import genetic.api.individual.impl.RatedIndividual

interface ElitistPicker {

  fun <T> pick(fitnessTesteds: Iterable<RatedIndividual<T>>): Sequence<Individual<T>>
}