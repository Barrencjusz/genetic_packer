package pl.piteron.genetic.packer

import com.fasterxml.jackson.databind.ObjectMapper
import genetic.Evolution
import genetic.EvolutionAutoConfiguration
import genetic.api.fitness.FitnessTester
import genetic.api.fitness.FitnessTranslator
import genetic.api.fitness.TranslatedFitness
import genetic.api.generation.GenerationContext
import genetic.api.individual.Individual
import genetic.api.individual.Organism
import genetic.api.individual.impl.SimpleIndividual
import genetic.api.mutation.Mutator
import genetic.recombinators.Recombinator
import genetic.recombinators.chromosome.SingleChromosomeRecombinator
import genetic.recombinators.chromosome.TwoChromosomesRecombinator
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.pow

@SpringBootApplication
class Runner(
    private val evolution: Evolution<Body, Embryo>,
    private val objectMapper: ObjectMapper
) : CommandLineRunner {
  override fun run(vararg args: String?) {
    TODO("Not yet implemented")
  }

  //  @OptIn(ExperimentalTime::class)
  //  override fun run(vararg args: String) {
  //    val time = measureTimedValue {
  //      val boxes = listOf(
  //          Box(1, 4, 6),
  //          Box(2, 4, 6),
  //          Box(3, 4, 6),
  //          Box(4, 4, 6),
  //          Box(5, 4, 6),
  //          Box(6, 4, 6),
  //          Box(7, 4, 6),
  //          Box(8, 4, 6),
  //          Box(9, 4, 6),
  //          Box(10, 4, 6),
  //          Box(11, 1, 30),
  //          Box(12, 4, 6)
  //      )
  //      evolution(
  //          Evolution.Context(
  //              numberOfGenerations = 100,
  //              generationSize = (sqrt(boxes.size.toDouble()) * 10).toInt(),
  //              numberOfEliteIndividuals = 2,
  //              embryo = Embryo(
  //                  containerWidth = 3,
  //                  containerDepth = 10,
  //                  boxes = boxes,
  //                  minSpace = boxes.fold(0) { space, box -> space + box.width * box.depth }
  //              )
  //          )
  //      )
  //    }
  //    println("Evolution took ${time.duration}")
  //    println(
  //        objectMapper.writerWithDefaultPrettyPrinter()
  //            .writeValueAsString(time.value)
  //    )
  //  }

}

fun main(args: Array<String>) {
  runApplication<Runner>(*args)
}

@Configuration
class PackerConfiguration : EvolutionAutoConfiguration<Embryo, Body>() {

  @Bean
  fun individualCreator(random: () -> ThreadLocalRandom) = IndividualCreatorImpl(random)

  @Bean
  fun singleChildRecombinator(
      singleChromosomeRecombinator: Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>, Embryo>,
      random: () -> ThreadLocalRandom
  ) =
      object : Recombinator<Organism<Body>, Individual<Body>, Embryo> {

        override fun invoke(pair: Pair<Organism<Body>, Organism<Body>>, embryo: Embryo) =
            pair.let {
              Pair(
                  first = it.first.body.boxes.asSequence(), //FIXME sequences?
                  second = it.second.body.boxes.asSequence()
              )
            }
                .let { singleChromosomeRecombinator(it, embryo) }
                .map {
                  createNewIndividual(random(), embryo, it)
                }
      }

  @Bean
  fun twoChildrenRecombinator(
      twoChromosomesRecombinator: Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>, Embryo>,
      random: () -> ThreadLocalRandom
  ) =
      object : Recombinator<Organism<Body>, Individual<Body>, Embryo> {

        override fun invoke(pair: Pair<Organism<Body>, Organism<Body>>, embryo: Embryo) =
            pair.let {
              Pair(
                  first = it.first.body.boxes.asSequence(), //FIXME sequences?
                  second = it.second.body.boxes.asSequence()
              )
            }
                .let { twoChromosomesRecombinator(it, embryo) }
                .map {
                  createNewIndividual(random(), embryo, it)
                }
      }

  private fun createNewIndividual(
      random: ThreadLocalRandom,
      embryo: Embryo,
      boxes: Sequence<PositionedBox>
  ): SimpleIndividual<Body> {
    val probability = 0.03
    val container = Container(embryo.containerWidth, embryo.containerDepth)
    val positionedBoxes =
        boxes.map { (if (random.nextDouble() < probability) random.nextInt() else it.loadingOrder) to it }
            .sortedBy { it.first }
            .map { (loadingOrder, positionedBox) ->
              val rotated = if (positionedBox.box.canBeRotated && random.nextDouble() < probability) {
                random.nextBoolean()
              } else positionedBox.rotated
              val x = if (random.nextDouble() < probability) {
                random.nextInt(embryo.containerWidth - (if (rotated) positionedBox.box.depth else positionedBox.box.width) + 1)
              } else if (rotated != positionedBox.rotated && (positionedBox.x + if (rotated) positionedBox.box.depth else positionedBox.box.width) > embryo.containerWidth) {
                embryo.containerWidth - if (rotated) positionedBox.box.depth else positionedBox.box.width
              } else positionedBox.x
              val y = container.put(box = positionedBox.box, x = x, rotated = rotated)
              PositionedBox(box = positionedBox.box, x = x, y = y, rotated = rotated, loadingOrder = loadingOrder)
            }
            .sortedBy { it.box.id }

    return SimpleIndividual(
        Body(
            container = container,
            boxes = positionedBoxes.toList()
        )
    )
  }

  @Bean
  fun singleChromosomeRecombinator(random: () -> ThreadLocalRandom): Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>, Embryo> =
      SingleChromosomeRecombinator(random)

  @Bean
  fun twoChromosomesRecombinator(random: () -> ThreadLocalRandom): Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>, Embryo> =
      TwoChromosomesRecombinator(random)

  @Bean
  fun mutator() = object : Mutator<Int, Int> {
    override fun invoke(p1: Organism<Int>, p2: Int) {

    }
  }

  //  @Bean
  //  fun fitnessTester(objectMapper: ObjectMapper) = object : FitnessTester<Body, Embryo> {
  //
  //    override fun invoke(individual: Individual<Body>, embryo: Embryo): TranslatedFitness<*> {
  //      val statistics = individual.body.container.generateStatistics()
  //      return ContainerFitness(
  //          components = ContainerFitness.Components(
  //              depthScore = (1.0 / statistics.usedDepth).pow(0.95) * 20000,
  //              usedSpaceScore = 1.0 / (statistics.usedSpace.toDouble() / embryo.minSpace) * 200,
  //              usedWidthScore = 1.0 / (statistics.usedWidth.toDouble() / embryo.containerWidth) * 20,
  //              deepestMiddleEdgeScore = (statistics.deepestMiddleEdge * embryo.containerWidth.toDouble() / embryo.minSpace) * 10,
  //              depressionLengthScore = (1.0 / statistics.depressionLength.toDouble()) * 500,
  //              numberOfEdgesScore = 1.0 / statistics.numberOfEdges / 10,
  //              historyOfEdgesScore = {
  //                var previous = 0
  //                statistics.historyOfEdges.fold(1.0) { acc, current ->
  //                  val score = if (previous < current) 1.0 else 1.1
  //                  previous = current
  //                  acc * score
  //                }
  //              }(),
  //              totalDistanceFromUsedDepthScore = 1.0 / (statistics.totalDistanceFromUsedDepth + 1)
  //          ),
  //          totalFitnessTranslator = object : FitnessTranslator<ContainerFitness, Double> {
  //
  //            override fun apply(fitness: ContainerFitness) =
  //                fitness.components.depthScore +
  //                    fitness.components.usedSpaceScore +
  //                    fitness.components.usedWidthScore +
  //                    fitness.components.deepestMiddleEdgeScore + /*deepestEdgeScore +*/
  //                    fitness.components.depressionLengthScore +
  //                    fitness.components.numberOfEdgesScore +
  //                    fitness.components.historyOfEdgesScore +
  //                    fitness.components.totalDistanceFromUsedDepthScore
  //          },
  //          fitnessExplainedTranslator = object : FitnessTranslator<ContainerFitness, String> {
  //
  //            override fun apply(fitness: ContainerFitness) = objectMapper.writerWithDefaultPrettyPrinter()
  //                .writeValueAsString(fitness.components)
  //          }
  //      )
  //    }
  //  }

  @Bean
  fun fitnessTester(objectMapper: ObjectMapper) = object : FitnessTester<Body, Embryo> {

    override fun invoke(individual: Individual<Body>, embryo: Embryo): TranslatedFitness<*> {
      val statistics = individual.body.container.generateStatistics()
      return ContainerFitness(
          components = ContainerFitness.Components(
              depthScore = embryo.containerDepth / statistics.usedDepth.toDouble().pow(1.2),
              numberOfBoxesThatWastedSpaceScore = individual.body.boxes.size / (statistics.numberOfBoxesThatWastedSpace.toDouble() + 1),
              usedSpaceScore = embryo.minSpace / statistics.usedSpace.toDouble(),
              usedWidthScore = embryo.containerWidth / statistics.usedWidth.toDouble(),
              deepestMiddleEdgeScore = statistics.deepestMiddleEdge / statistics.usedDepth.toDouble(),
              depressionLengthScore = 1.0 / statistics.depressionLength,
              numberOfEdgesScore = 1.0 / statistics.numberOfEdges,
//              historyOfEdgesScore = {
//                var previous = 0
//                statistics.historyOfEdges.fold(1.0) { acc, current ->
//                  val score = if (previous < current) 1.0 else 1.1
//                  previous = current
//                  acc * score
//                }
//              }(),
              totalDistanceFromUsedDepthScore = 1.0 / (statistics.totalDistanceFromUsedDepth + 1)
          ),
          totalFitnessTranslator = object : FitnessTranslator<ContainerFitness, Double> {

            override fun apply(fitness: ContainerFitness) =
                fitness.components.depthScore *
                    (1 + fitness.components.usedSpaceScore / 5) *
                    (1 + fitness.components.numberOfBoxesThatWastedSpaceScore / 30) *
                    //(1 + fitness.components.usedWidthScore / 100) *
                    (1 + fitness.components.deepestMiddleEdgeScore / 200) * /*deepestEdgeScore +*/
                    (1 + fitness.components.depressionLengthScore / 300) *
                    (1 + fitness.components.numberOfEdgesScore / 400) *
//                    (1 + fitness.components.historyOfEdgesScore / 1000) *
                    (1 + fitness.components.totalDistanceFromUsedDepthScore / 5000)
          },
          fitnessExplainedTranslator = object : FitnessTranslator<ContainerFitness, String> {

            override fun apply(fitness: ContainerFitness) = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(fitness.components) + "\nTotal score: ${fitness.score}"
          }
      )
    }
  }

  class ContainerFitness(
      val components: Components,
      totalFitnessTranslator: FitnessTranslator<ContainerFitness, Double>,
      fitnessExplainedTranslator: FitnessTranslator<ContainerFitness, String>
  ) : TranslatedFitness<ContainerFitness>(totalFitnessTranslator, fitnessExplainedTranslator) {

    override fun self() = this

    data class Components(
        val depthScore: Double,
        val usedSpaceScore: Double,
        val numberOfBoxesThatWastedSpaceScore: Double,
        val usedWidthScore: Double,
        val deepestMiddleEdgeScore: Double, /*deepestEdgeScore,*/
        val depressionLengthScore: Double,
        val numberOfEdgesScore: Double,
        //val historyOfEdgesScore: Double,
        val totalDistanceFromUsedDepthScore: Double
    )
  }

  @Bean
  fun contextMapper() = object : Evolution.ContextMapper<Embryo> {
    override fun invoke(context: Evolution.Context<Embryo>) =
        GenerationContext(
            embryo = context.embryo,
            chromosomeSize = 0,
            generationSize = context.generationSize,
            numberOfEliteIndividuals = context.numberOfEliteIndividuals
        )
  }
}