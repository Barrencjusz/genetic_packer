package genetic.api.individual.impl

import genetic.api.elitism.EliteAcknowledged
import genetic.api.elitism.Promoter
import genetic.api.fitness.Evaluator
import genetic.api.individual.Individual
import genetic.api.individual.Promoted

class Elite<T>(
    ratedIndividual: RatedIndividual<T>
) : RatedIndividual<T>(ratedIndividual.fitness, ratedIndividual), Individual<T>, Promoted<T> {

  override fun <M> evaluate(evaluator: M) where M : Evaluator, M : EliteAcknowledged =
      evaluator(this)

  override fun <M> promote(promoter: M) where M : Promoter, M : EliteAcknowledged =
      promoter(this)
}