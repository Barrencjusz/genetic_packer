package genetic.packer.fx.calculation

import genetic.packer.fx.specification.BoundsGetter
import javafx.geometry.Bounds

interface BoundingSizeCalculator : (Pair<BoundsGetter, BoundsGetter>) -> Double {

  interface Creator {

    fun from(bounds: Sequence<Bounds>): BoundingSizeCalculator
  }
}