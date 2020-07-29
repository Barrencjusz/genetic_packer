package genetic.api.generation

import genetic.api.individual.impl.RatedIndividual

data class Generation<T>(
    val number: Int,
    val ratedIndividuals: Sequence<RatedIndividual<T>>
)