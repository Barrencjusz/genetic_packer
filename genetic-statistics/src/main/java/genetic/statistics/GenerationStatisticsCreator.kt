package genetic.statistics

import genetic.api.generation.Generation
import genetic.api.statistics.GenerationStatistics

interface GenerationStatisticsCreator {

  fun create(generations: Sequence<Generation<*>>): Sequence<GenerationStatistics>
}