package genetic.evaluator

import genetic.api.elitism.EliteAcknowledged
import genetic.api.fitness.Evaluator
import genetic.api.fitness.FitnessTester
import genetic.api.individual.Individual
import genetic.api.individual.impl.RatedIndividual

class EvaluatorImpl<T, U>(
    private val fitnessTester: FitnessTester<T, U>,
    private val embryo: U
) : Evaluator<T>, EliteAcknowledged {

  override fun invoke(individual: Individual<T>) = RatedIndividual(
      fitness = fitnessTester(individual, embryo),
      organism = individual
  )
}