package pl.piteron.genetic.packer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GreatestCommonDivisorCalculatorTest {

  @Test
  fun test() {
    val result = GreatestCommonDivisorCalculator.calculate(listOf(80, 120, 200, 300))
    assertThat(result).isEqualTo(20)
  }
}