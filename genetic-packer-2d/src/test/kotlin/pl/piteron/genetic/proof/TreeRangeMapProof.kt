package pl.piteron.genetic.proof

import com.google.common.collect.Range
import com.google.common.collect.TreeRangeMap
import org.junit.jupiter.api.Test

class TreeRangeMapProof {

  @Test
  fun test() {
    val map = TreeRangeMap.create<Int, Int>()
    map.put(Range.closedOpen(0, 2), 2)
    map.put(Range.closedOpen(2, 3), 5)
    map.put(Range.closedOpen(3, 7), 5)
    map.put(Range.closedOpen(2, 5), 7)

    println(map)

    val subRangeMap = map.subRangeMap(Range.closedOpen(3, 6))
    val max = subRangeMap.asMapOfRanges().values.max()
    println(max)

  }
}