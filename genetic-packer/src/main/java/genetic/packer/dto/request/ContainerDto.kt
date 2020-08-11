package genetic.packer.dto.request

import genetic.packer.dto.BoxDto

data class ContainerDto(
    override val width: Int,
    override val height: Int,
    override val depth: Int
) : BoxDto