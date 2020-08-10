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
import kotlin.math.sqrt
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@SpringBootApplication
class Runner(
    private val evolution: Evolution<Body, Embryo>,
    private val objectMapper: ObjectMapper
) : CommandLineRunner {

  @OptIn(ExperimentalTime::class)
  override fun run(vararg args: String) {
    val time = measureTimedValue {
      val boxes = listOf(
          Box(1, 4, 6),
          Box(2, 4, 6),
          Box(3, 4, 6),
          Box(4, 4, 6),
          Box(5, 4, 6),
          Box(6, 4, 6),
          Box(7, 4, 6),
          Box(8, 4, 6),
          Box(9, 4, 6),
          Box(10, 4, 6),
          Box(11, 1, 30),
          Box(12, 4, 6)
      )
      evolution(
          Evolution.Context(
              numberOfGenerations = 100,
              generationSize = (sqrt(boxes.size.toDouble()) * 10).toInt(),
              embryo = Embryo(
                  containerWidth = 3,
                  containerDepth = 10,
                  boxes = boxes
              ),
              numberOfTopIndividuals = 1,
              numberOfEliteIndividuals = 2
          )
      )
    }
    println("Evolution took ${time.duration}")
    println(
        objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(time.value)
    )
  }

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
      singleChromosomeRecombinator: Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>>,
      random: () -> ThreadLocalRandom
  ) =
      object : Recombinator<Organism<Body>, Individual<Body>> {

        override fun invoke(pair: Pair<Organism<Body>, Organism<Body>>) =
            pair.let {
              Pair(
                  first = it.first.body.boxes.asSequence(), //FIXME sequences?
                  second = it.second.body.boxes.asSequence()
              )
            }
                .let { singleChromosomeRecombinator(it) }
                .map {
                  createNewIndividual(random(), pair, it)
                }
      }

  @Bean
  fun twoChildrenRecombinator(
      twoChromosomesRecombinator: Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>>,
      random: () -> ThreadLocalRandom
  ) =
      object : Recombinator<Organism<Body>, Individual<Body>> {

        override fun invoke(pair: Pair<Organism<Body>, Organism<Body>>) =
            pair.let {
              Pair(
                  first = it.first.body.boxes.asSequence(), //FIXME sequences?
                  second = it.second.body.boxes.asSequence()
              )
            }
                .let { twoChromosomesRecombinator(it) }
                .map {
                  createNewIndividual(random(), pair, it)
                }
      }

  private fun createNewIndividual(
      random: ThreadLocalRandom,
      pair: Pair<Organism<Body>, Organism<Body>>,
      it: Sequence<PositionedBox>
  ): SimpleIndividual<Body> {
    val oldContainer = pair.first.body.container
    val container = Container(oldContainer.width, oldContainer.depth)
    val positionedBoxes = it.asSequence()
        .map { (if (random.nextDouble() < 0.05) random.nextInt() else it.loadingOrder) to it }
        .sortedBy { it.first }
        .map { (loadingOrder, positionedBox) ->
          val rotated =
              if (random.nextDouble() < 0.05) { //FIXME should be calculated once at startup
                when {
                  positionedBox.box.width > oldContainer.width -> true // FIXME pass embryo?
                  positionedBox.box.depth > oldContainer.width -> false
                  else -> random.nextBoolean()
                }
              } else positionedBox.rotated
          if (random.nextDouble() < 0.05) random.nextBoolean() else positionedBox.rotated
          val x = if (random.nextDouble() < 0.05) {
            random.nextInt(oldContainer.width + 1 - if (rotated) positionedBox.box.depth else positionedBox.box.width)
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
  fun singleChromosomeRecombinator(random: () -> ThreadLocalRandom): Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>> =
      SingleChromosomeRecombinator(random)

  @Bean
  fun twoChromosomesRecombinator(random: () -> ThreadLocalRandom): Recombinator<Sequence<PositionedBox>, Sequence<PositionedBox>> =
      TwoChromosomesRecombinator(random)

  @Bean
  fun mutator() = object : Mutator<Int, Int> {
    override fun invoke(p1: Organism<Int>, p2: Int) {

    }
  }

  @Bean
  fun fitnessTester() = object : FitnessTester<Body> {

    override fun invoke(individual: Individual<Body>): TranslatedFitness<*> = SomeTranslatedFitness(
        object : FitnessTranslator<SomeTranslatedFitness, Double> {

          override fun apply(fitness: SomeTranslatedFitness) = with(individual.body.container) {
            (
                if (this.violated) 0.0 else {
                  val depthScore = (1.0 / this.usedDepth).pow(0.1) * 1000
                  val usedSpaceScore = 1.0 / this.usedSpace * 200
                  val usedWidthScore = 1.0 / this.usedWidth / this.width / 1000
                  val deepestEdgeScore = this.deepestEdge / 100
                  val numberOfEdgesScore = 1.0 / this.numberOfEdges / 10000
                  depthScore + usedSpaceScore + usedWidthScore + deepestEdgeScore + numberOfEdgesScore
                }
                )
          }
        },
        object : FitnessTranslator<SomeTranslatedFitness, String> {

          override fun apply(fitness: SomeTranslatedFitness) = "what what"
        }
    )
  }

  class SomeTranslatedFitness(
      totalFitnessTranslator: FitnessTranslator<SomeTranslatedFitness, Double>,
      fitnessExplainedTranslator: FitnessTranslator<SomeTranslatedFitness, String>
  ) : TranslatedFitness<SomeTranslatedFitness>(totalFitnessTranslator, fitnessExplainedTranslator) {
    override fun self(): SomeTranslatedFitness {
      return this
    }
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