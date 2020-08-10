package pl.piteron.genetic.packer

import com.google.common.collect.Range
import com.google.common.collect.TreeRangeMap
import kotlin.math.absoluteValue

class Container(val width: Int, val depth: Int) {

  private val map = TreeRangeMap.create<Int, Int>()

  fun put(box: Box, x: Int, rotated: Boolean = false): Int {
    val range = Range.closedOpen(x, x + if (rotated) box.depth else box.width)
    val usedDepth = map.subRangeMap(range)
        .asMapOfRanges()
        .maxBy { it.value }?.value ?: 0

    map.putCoalescing(range, usedDepth + if (rotated) box.width else box.depth)
    return usedDepth
  }

  val usedDepth by lazy { this.asMapOfRanges.values.max() ?: 0 }

  val usedWidth by lazy {
    this.span.let { it.lowerEndpoint() - it.upperEndpoint() }.absoluteValue
  }

  val usedSpace by lazy {
    this.asMapOfRanges.entries.fold(initial = 0, operation = { space, entry ->
      space + entry.key.let { it.upperEndpoint() - it.lowerEndpoint() } * entry.value
    })
  }

  val numberOfEdges by lazy {
    this.asMapOfRanges.size
  }

  val deepestEdge by lazy {
    this.asMapOfRanges.values.min()!!
  }

  val violated by lazy {
    this.span
        .upperEndpoint() > width
  }

  private val span by lazy {
    map.span()
  }

  private val asMapOfRanges by lazy {
    map.asMapOfRanges()
  }
}