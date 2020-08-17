package pl.piteron.genetic.packer

import genetic.api.individual.Individual
import genetic.api.individual.IndividualCreator
import genetic.api.individual.impl.SimpleIndividual
import java.util.concurrent.ThreadLocalRandom

class IndividualCreatorImpl(
    private val random: () -> ThreadLocalRandom
) : IndividualCreator<Embryo, Body> {

  override fun invoke(embryo: Embryo): () -> Individual<Body> = {
    val random = random()
    val container = Container(embryo.containerWidth, embryo.containerDepth)
    val positionedBoxes = embryo.boxes.asSequence()
        .map { random.nextInt() to it }
        .sortedBy { it.first }
        .map { (loadingOrder, box) ->
          val rotated = if (box.canBeRotated) random.nextBoolean() else false
          val x = random.nextInt(embryo.containerWidth - (if (rotated) box.depth else box.width) + 1)
          val y = container.put(box = box, x = x, rotated = rotated)
          PositionedBox(box = box, x = x, y = y, rotated = rotated, loadingOrder = loadingOrder)
        }
        .sortedBy { it.box.id }

    SimpleIndividual(
        Body(
            container = container,
            boxes = positionedBoxes.toList()
        )
    )
  }
}