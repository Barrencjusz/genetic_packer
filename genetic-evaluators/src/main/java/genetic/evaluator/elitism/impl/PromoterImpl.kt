package genetic.evaluator.elitism.impl

import genetic.api.elitism.EliteAcknowledged
import genetic.api.elitism.Promoter
import genetic.api.individual.impl.Elite
import genetic.api.individual.impl.RatedIndividual

class PromoterImpl : Promoter, EliteAcknowledged {

  fun <T> acknowledge(elite: Elite<T>) = elite

  override fun <T> invoke(ratedIndividual: RatedIndividual<T>) = Elite(ratedIndividual)
}