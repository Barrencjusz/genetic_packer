package genetic.packer.dto.response

data class IndividualDto(
    val fitness: Double,
    val numberOfGeneration: Int,
    val translatedBoxes: Sequence<TranslatedBoxDto>
)