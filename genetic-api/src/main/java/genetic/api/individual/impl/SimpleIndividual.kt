package genetic.api.individual.impl

import genetic.api.elitism.EliteAcknowledged
import genetic.api.fitness.Evaluator
import genetic.api.individual.Individual

class SimpleIndividual<T>(
    override val body: T
) : Individual<T> {

  override fun <M> evaluate(evaluator: M): RatedIndividual<T> where M : Evaluator<T>, M : EliteAcknowledged =
      evaluator(this)
}