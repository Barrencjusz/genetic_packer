package genetic.packer.evolution.fitness

import javafx.scene.shape.Box
import java.util.function.BiPredicate

class IntersectionFitnessTester(
    private val nonTangentialIntersectionResolver: BiPredicate<Box, Box>
) : (Pair<BoxScoreProperties, BoxScoreProperties>) -> Unit {

  override fun invoke(pair: Pair<BoxScoreProperties, BoxScoreProperties>) {
    val (left, right) = pair
    if (nandIntersects(left, right) && nonTangentialIntersectionResolver.test(left.box, right.box)) {
      left.isIntersects = true
      right.isIntersects = true
    }
  }

  private fun nandIntersects(left: BoxScoreProperties, right: BoxScoreProperties) =
      !(left.isIntersects && right.isIntersects)
}