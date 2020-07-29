package genetic.packer.dto.response

import genetic.packer.dto.BoxDto
import genetic.api.statistics.GenerationStatistics

data class ResponseDto(
    val container: BoxDto,
    val topIndividuals: Sequence<IndividualDto>,
    val generationStats: Sequence<GenerationStatistics>
)