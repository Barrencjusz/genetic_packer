package genetic.evaluator.elitism.impl

import genetic.api.individual.impl.RatedIndividual
import genetic.evaluator.elitism.ElitistPicker

class ElitistPickerImpl(
    private val promoter: PromoterImpl // FIXME not impl
) : ElitistPicker {

  override fun <T> pick(
      fitnessTesteds: Sequence<RatedIndividual<T>>,
      count: Int
  ) = fitnessTesteds.sortedByDescending { it.fitness.score }
      .take(count)
      .map { it.promote(promoter) }
}