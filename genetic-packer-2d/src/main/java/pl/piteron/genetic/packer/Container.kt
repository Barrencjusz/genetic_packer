package pl.piteron.genetic.packer

import com.google.common.collect.Range
import com.google.common.collect.TreeRangeMap
import kotlin.math.absoluteValue

class Container(val width: Int, val depth: Int) {

  private val map = TreeRangeMap.create<Int, Int>()
  private val historyOfEdges = mutableListOf<Int>()

  fun put(box: Box, x: Int, rotated: Boolean = false): Int {
    val range = Range.closedOpen(x, x + if (rotated) box.depth else box.width)
    val usedDepth = map.subRangeMap(range)
        .asMapOfRanges()
        .maxBy { it.value }?.value ?: 0

    map.put(range, usedDepth + if (rotated) box.width else box.depth) // FIXME putCoalescing heavy?
    historyOfEdges.add(map.asMapOfRanges().size)
    return usedDepth
  }

  fun generateStatistics(): Statistics {
    val span = map.span()
    val mapOfRanges = map.asMapOfRanges()
    val mapOfRangesEntries = mapOfRanges.entries
    val mapOfRangesValues = mapOfRanges.values
    val subRangeMap = map.subRangeMap(span)
    val subRangeMapValues = subRangeMap.asMapOfRanges().values

    val usedDepth = mapOfRangesValues.max()!!
    val usedWidth = span.let { it.lowerEndpoint() - it.upperEndpoint() }.absoluteValue
    val usedSpace = mapOfRangesEntries.fold(initial = 0, operation = { space, entry ->
      space + entry.key.let { it.upperEndpoint() - it.lowerEndpoint() } * entry.value
    })
    val numberOfEdges = mapOfRanges.size
    val deepestEdge = mapOfRangesValues.min()!!
    val deepestMiddleEdge = subRangeMapValues.min()!!
    val depressionLength = {
      var previous = 0
      subRangeMapValues.sumBy {
        val difference = (previous - it).absoluteValue
        previous = it
        difference
      }
    }()
    return Statistics(
        usedDepth = usedDepth,
        usedWidth = usedWidth,
        usedSpace = usedSpace,
        numberOfEdges = numberOfEdges,
        deepestEdge = deepestEdge,
        deepestMiddleEdge = deepestMiddleEdge,
        depressionLength = depressionLength,
        historyOfEdges = historyOfEdges
    )
  }

  data class Statistics(
      val usedDepth: Int,
      val usedWidth: Int,
      val usedSpace: Int,
      val numberOfEdges: Int,
      val deepestEdge: Int,
      val deepestMiddleEdge: Int,
      val depressionLength: Int,
      val historyOfEdges: List<Int>
  )
}