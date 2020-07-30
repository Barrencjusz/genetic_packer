package genetic.packer.fx.calculation.impl

import genetic.packer.fx.calculation.BoundingSizeCalculator
import genetic.packer.fx.specification.BoundsGetter
import javafx.geometry.Bounds
import org.springframework.stereotype.Component

@Component
class EdgeVolumeCalculator(
    private val boundsGetters: Set<Pair<BoundsGetter, BoundsGetter>>,
    private val boundingSizeCalculatorCreator: BoundingSizeCalculator.Creator
) : (Sequence<Bounds>) -> Double {

  override fun invoke(allBoxesBounds: Sequence<Bounds>) =
      with(boundingSizeCalculatorCreator.from(allBoxesBounds)) {
        boundsGetters
            .map { this(it) }
            .reduce { a, b -> a * b }
      }
}