package genetic.api.generation

class GenerationContext<T>(
    val embryo: T,
    val chromosomeSize: Int,
    val generationSize: Int,
    val numberOfEliteIndividuals: Int
)