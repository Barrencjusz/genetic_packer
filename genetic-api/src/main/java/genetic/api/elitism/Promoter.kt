package genetic.api.elitism

import genetic.api.individual.impl.Elite
import genetic.api.individual.impl.RatedIndividual

interface Promoter {

  operator fun <T> invoke(ratedIndividual: RatedIndividual<T>): Elite<T>
}