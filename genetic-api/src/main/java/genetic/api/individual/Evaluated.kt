package genetic.api.individual

import genetic.api.elitism.EliteAcknowledged
import genetic.api.fitness.Evaluator
import genetic.api.individual.impl.RatedIndividual

interface Evaluated<T> {

  fun <M> evaluate(evaluator: M): RatedIndividual<T> where M : Evaluator, M : EliteAcknowledged
}