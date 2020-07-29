package pl.piteron.genetic.packer

import com.google.common.collect.Range
import com.google.common.collect.TreeRangeMap

class Container(private val width: Int, private val depth: Int) {

  private val map = TreeRangeMap.create<Int, Int>()

  fun put(box: Box, position: Int): PositionedBox {
    val range = Range.closedOpen(position, if (box.rotated) box.depth else box.width)
    val usedDepth = map.subRangeMap(range).asMapOfRanges().values.max() ?: 0
    map.putCoalescing(range, usedDepth + if (box.rotated) box.width else box.depth)
    return PositionedBox(box, position, usedDepth)
  }

  fun getUsedDepth() = map.asMapOfRanges().values.max() ?: 0
}