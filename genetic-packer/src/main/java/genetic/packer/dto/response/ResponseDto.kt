package genetic.packer.dto.response

import genetic.api.statistics.GenerationStatistics
import genetic.packer.dto.BoxDto

data class ResponseDto(
    val container: BoxDto,
    val topIndividuals: Sequence<IndividualDto>,
    val generationStats: Sequence<GenerationStatistics>
)