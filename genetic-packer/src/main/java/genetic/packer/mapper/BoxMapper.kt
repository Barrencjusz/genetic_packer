package genetic.packer.mapper

import genetic.packer.dto.BoxDto
import genetic.packer.dto.response.TranslatedBoxDto
import genetic.packer.dto.response.TranslationDto
import javafx.scene.shape.Box

class BoxMapper {

  fun map(boxDto: BoxDto) = Box(
      boxDto.width.toDouble(),
      boxDto.height.toDouble(),
      boxDto.depth.toDouble()
  )

  fun map(box: Box) = TranslatedBoxDto(
      width = box.width.toInt(),
      height = box.height.toInt(),
      depth = box.depth.toInt(),
      position = TranslationDto(
          x = box.boundsInParent
              .minX
              .toInt(),
          y = box.boundsInParent
              .minY
              .toInt(),
          z = box.boundsInParent
              .minZ
              .toInt()
      )
  )
}