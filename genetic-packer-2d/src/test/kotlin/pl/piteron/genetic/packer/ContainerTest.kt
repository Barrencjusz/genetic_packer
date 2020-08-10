package pl.piteron.genetic.packer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContainerTest {

  @Test
  fun test() {
    val container = Container(width = 2, depth = 3)
    val y1 = container.put(Box(1, 1, 1), 0)
    val y2 = container.put(Box(2, 1, 2), 0, true)
    val y3 = container.put(Box(3, 2, 1), 0)
    assertThat(y1).isEqualTo(0)
    assertThat(y2).isEqualTo(1)
    assertThat(y3).isEqualTo(2)
    assertThat(container.usedDepth).isEqualTo(3)
    assertThat(container.usedSpace).isEqualTo(6)
  }
}