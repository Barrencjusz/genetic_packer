package genetic.api.fitness

import genetic.api.individual.Individual
import genetic.api.individual.impl.RatedIndividual

interface Evaluator<T> {

  operator fun invoke(individual: Individual<T>): RatedIndividual<T>
}