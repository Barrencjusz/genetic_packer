package genetic.packer.evolution.fitness.impl

abstract class BaseFitnessTranslator {

  protected fun rateIntersections(intersections: Sequence<Boolean>) = intersections
      .map { rateSingleIntersection(it) }
      .reduce { left, right -> left * right }

  private fun rateSingleIntersection(intersects: Boolean) = if (intersects) 1.0 else 1.5
}