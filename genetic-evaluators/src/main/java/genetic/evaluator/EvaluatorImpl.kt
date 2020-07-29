package genetic.evaluator

import genetic.api.elitism.EliteAcknowledged
import genetic.api.fitness.Evaluator
import genetic.api.fitness.TranslatedFitness
import genetic.api.individual.Individual
import genetic.api.individual.impl.RatedIndividual

class EvaluatorImpl(
    private val fitnessTester: (Individual<*>) -> TranslatedFitness<*>
) : Evaluator, EliteAcknowledged {

  override fun <P> invoke(individual: Individual<P>) = RatedIndividual(
      fitness = fitnessTester(individual),
      organism = individual
  )
}