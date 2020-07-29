package genetic.packer.factory

import javafx.geometry.BoundingBox

object ContainerBoundsFactory {

  fun createBounds(width: Double, height: Double, depth: Double) = BoundingBox(
      0.0,
      0.0,
      0.0,
      width,
      height,
      depth
  )
}