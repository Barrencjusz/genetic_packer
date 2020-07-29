package genetic.packer.mapper

import genetic.api.individual.Cell
import javafx.scene.shape.Box
import org.mapstruct.Mapper
import java.util.function.Function

abstract class CellCloningMapper : (Cell<Box>) -> Cell<Box> {

  override fun invoke(cell: Cell<Box>): Cell<Box> {
    val box = cell.nucleus
    val clonedBox = Box(box.width, box.height, box.depth)
    clonedBox.translateX = box.translateX
    clonedBox.translateY = box.translateY
    clonedBox.translateZ = box.translateZ
    return Cell(
        nucleus = clonedBox,
        processingOrder = cell.processingOrder,
        order = cell.order
    )
  }
}