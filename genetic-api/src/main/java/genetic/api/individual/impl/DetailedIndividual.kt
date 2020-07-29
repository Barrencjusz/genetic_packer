package genetic.api.individual.impl

class DetailedIndividual<T>(
    ratedIndividual: RatedIndividual<T>,
    val numberOfGeneration: Int
) : RatedIndividual<T>(ratedIndividual.fitness, ratedIndividual)