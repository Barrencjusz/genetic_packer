package genetic.packer.dto.request

import genetic.packer.dto.BoxDto

data class EmbryoDto(
    val container: ContainerDto,
    val boxes: Sequence<BoxDto>
)