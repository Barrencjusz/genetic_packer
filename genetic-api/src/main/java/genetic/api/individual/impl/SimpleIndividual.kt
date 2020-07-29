package genetic.api.individual.impl

import genetic.api.elitism.EliteAcknowledged
import genetic.api.fitness.Evaluator
import genetic.api.individual.Cell
import genetic.api.individual.Individual

class SimpleIndividual<T>(
    override val cells: Sequence<Cell<T>>
) : Individual<T> {

  override fun <M> evaluate(evaluator: M): RatedIndividual<T> where M : Evaluator, M : EliteAcknowledged =
      evaluator(this)
}