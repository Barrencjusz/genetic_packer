package genetic.api.statistics

data class GenerationStatistics(
    val generationNumber: Int,
    val bestFitness: Double,
    val averageFitness: Double,
    val bestFitnessExplained: String, //todo some generic self-explaining function?
    val averageNaturalFitness: Double
)