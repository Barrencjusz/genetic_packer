package pl.piteron.genetic.packer

object GreatestCommonDivisorCalculator {

  fun calculate(a: Int, b: Int): Int = if (a == 0) b else calculate(b % a, a)

  fun calculate(numbers: Iterable<Int>): Int {
    var result = 0
    numbers.forEach {
      result = calculate(result, it)
      if (result == 1) {
        return 1
      }
    }
    return result
  }
}