package genetic.packer.mapper

import genetic.packer.dto.request.ContainerDto
import genetic.packer.factory.ContainerBoundsFactory
import javafx.geometry.Bounds

abstract class ContainerToBoundsMapper : (ContainerDto) -> Bounds {

  override fun invoke(containerDto: ContainerDto) = ContainerBoundsFactory.createBounds(
      width = containerDto.width.toDouble(),
      height = containerDto.height.toDouble(),
      depth = containerDto.depth.toDouble()
  )
}