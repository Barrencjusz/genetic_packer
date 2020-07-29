package genetic.packer.evolution.fitness.impl

import genetic.api.individual.Individual
import genetic.packer.evolution.fitness.BoxScoreProperties
import genetic.packer.evolution.fitness.EveryCombinationResolver
import genetic.packer.evolution.fitness.FitnessComponents
import genetic.api.fitness.FitnessTranslator
import javafx.geometry.Bounds
import javafx.scene.shape.Box

class FitnessTesterImpl(
    private val everyCombinationResolver: EveryCombinationResolver,
    private val intersectionFitnessTester: (Pair<BoxScoreProperties, BoxScoreProperties>) -> Unit,
    private val edgeVolumeCalculator: (Sequence<Bounds>) -> Double,
    private val totalFitnessTranslator: FitnessTranslator<FitnessComponents, Double>,
    private val fitnessExplainedTranslator: FitnessTranslator<FitnessComponents, String>
) : (Individual<Box>) -> FitnessComponents {

  override fun invoke(individual: Individual<Box>): FitnessComponents {
    val boxScorePropertiesList = individual.cells
        .map { it.nucleus }
        .map { BoxScoreProperties(it) }
    everyCombinationResolver(boxScorePropertiesList.toList())
        .forEach { intersectionFitnessTester(it) }
    val allBoxesBounds = individual.cells.map { it.nucleus.boundsInParent }
    return FitnessComponents(
        totalFitnessTranslator = totalFitnessTranslator,
        fitnessExplainedTranslator = fitnessExplainedTranslator,
        intersections = boxScorePropertiesList.map(BoxScoreProperties::isIntersects),
        volume = edgeVolumeCalculator(allBoxesBounds)
    )
  }
}