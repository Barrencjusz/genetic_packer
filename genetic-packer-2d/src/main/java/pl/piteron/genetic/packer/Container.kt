package pl.piteron.genetic.packer

import com.google.common.collect.Range
import com.google.common.collect.TreeRangeMap
import kotlin.math.absoluteValue

class Container(val width: Int, val depth: Int) {

  private val map = TreeRangeMap.create<Int, Int>()
  private val historyOfEdges = mutableListOf<Int>()
  private var numberOfBoxesThatWastedSpace = 0

  fun put(box: Box, x: Int, rotated: Boolean = false): Int {
    val range = Range.closedOpen(x, x + if (rotated) box.depth else box.width)
    val values = map.subRangeMap(range)
        .asMapOfRanges().values.toList()
    val usedDepth = values.max() ?: 0
    val minimum = values.min() ?: 0
    val wastedSpace = usedDepth != minimum

    map.put(range, usedDepth + if (rotated) box.width else box.depth) // FIXME putCoalescing heavy?
    //historyOfEdges.add(calculateNumberOfEdges(map.asMapOfRanges()))
    if (wastedSpace) numberOfBoxesThatWastedSpace++
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
    val usedSpace = mapOfRangesEntries.fold(initial = 0, operation = { space, entry ->
      space + entry.key.let { it.upperEndpoint() - it.lowerEndpoint() } * entry.value
    })
    val usedWidth = span.let { it.lowerEndpoint() - it.upperEndpoint() }.absoluteValue
    val numberOfEdges = calculateNumberOfEdges(mapOfRanges)
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
    val totalDistanceFromUsedDepth = mapOfRanges.values.fold(0) { acc, value -> acc + usedDepth - value }
    return Statistics(
        usedDepth = usedDepth,
        usedSpace = usedSpace,
        numberOfBoxesThatWastedSpace = numberOfBoxesThatWastedSpace,
        usedWidth = usedWidth,
        numberOfEdges = numberOfEdges,
        deepestEdge = deepestEdge,
        deepestMiddleEdge = deepestMiddleEdge,
        depressionLength = depressionLength,
        totalDistanceFromUsedDepth = totalDistanceFromUsedDepth,
        historyOfEdges = historyOfEdges
    )
  }

  private fun calculateNumberOfEdges(asMapOfRanges: MutableMap<Range<Int>, Int>): Int {
    var previous = 0
    return asMapOfRanges.entries.fold(0) { acc, current ->
      val count = if (previous != current.value) 1 else 0
      previous = current.value
      acc + count
    }
  }

  data class Statistics(
      val usedDepth: Int,
      val usedWidth: Int,
      val numberOfBoxesThatWastedSpace: Int,
      val usedSpace: Int,
      val numberOfEdges: Int,
      val deepestEdge: Int,
      val deepestMiddleEdge: Int,
      val depressionLength: Int,
      val totalDistanceFromUsedDepth: Int,
      val historyOfEdges: List<Int>
  )
}