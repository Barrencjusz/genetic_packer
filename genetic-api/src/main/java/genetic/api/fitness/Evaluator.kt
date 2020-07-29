package genetic.api.fitness

import genetic.api.individual.Individual
import genetic.api.individual.impl.RatedIndividual
import java.util.function.Function

interface Evaluator {

  operator fun <T> invoke(individual: Individual<T>): RatedIndividual<T>
}