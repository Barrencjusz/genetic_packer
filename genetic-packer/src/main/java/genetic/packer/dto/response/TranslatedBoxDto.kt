package genetic.packer.dto.response

import genetic.packer.dto.BoxDto

data class TranslatedBoxDto(
    override val width: Int,
    override val height: Int,
    override val depth: Int,
    val position: TranslationDto<Int>,
    val rotation: TranslationDto<Double>? = null
) : BoxDto