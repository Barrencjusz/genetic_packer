package genetic.packer.dto.response

data class GenerationStatisticsDto(
    val generationNumber: Int,
    val bestFitness: Double,
    val averageFitness: Double,
    val bestNaturalFitness: Double, //todo some generic self-explaining function?
    val averageNaturalFitness: Double
)