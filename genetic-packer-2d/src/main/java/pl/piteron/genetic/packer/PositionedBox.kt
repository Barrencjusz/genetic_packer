package pl.piteron.genetic.packer

data class PositionedBox(
    val box: Box,
    val x: Int,
    val y: Int,
    val rotated: Boolean,
    val loadingOrder: Int
)