package genetic.packer.evolution.generation.dto

import javafx.geometry.Bounds
import javafx.scene.shape.Box

data class Embryo(
    val bounds: Bounds,
    val boxes: Sequence<Box>
)