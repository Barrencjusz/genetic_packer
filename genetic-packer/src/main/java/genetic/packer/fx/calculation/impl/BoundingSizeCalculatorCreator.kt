package genetic.packer.fx.calculation.impl

import genetic.packer.fx.calculation.BoundingSizeCalculator
import genetic.packer.fx.specification.BoundsGetter
import javafx.geometry.Bounds
import org.springframework.stereotype.Component

@Component
class BoundingSizeCalculatorCreator : BoundingSizeCalculator.Creator {

  override fun from(bounds: Sequence<Bounds>) =
      object : BoundingSizeCalculator {

        override fun invoke(pair: Pair<BoundsGetter, BoundsGetter>): Double {
          pair.let { (minimumProperty, maximumProperty) ->
            val max = bounds.map { maximumProperty.apply(it) }
                .max() ?: 0.0
            val min = bounds.map { minimumProperty.apply(it) }
                .min() ?: 0.0
            return max - min
          }
        }
      }
}