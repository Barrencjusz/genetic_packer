package pl.piteron.genetic.packer

import com.fasterxml.jackson.databind.ObjectMapper
import genetic.Evolution
import genetic.EvolutionAutoConfiguration
import genetic.api.fitness.FitnessTranslator
import genetic.api.fitness.TranslatedFitness
import genetic.api.generation.GenerationContext
import genetic.api.individual.Cell
import genetic.api.individual.Individual
import genetic.api.individual.IndividualCreator
import genetic.api.individual.Organism
import genetic.api.individual.impl.SimpleIndividual
import genetic.api.mutation.Mutator
import genetic.recombinators.Recombinator
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@SpringBootApplication
class Runner(
    private val evolution: Evolution<Int, Int>,
    private val objectMapper: ObjectMapper
) : CommandLineRunner {

  @OptIn(ExperimentalTime::class)
  override fun run(vararg args: String) {
    (0..100).forEach {
      val time = measureTime {
        evolution(
            Evolution.Context(
                numberOfGenerations = 5000,
                generationSize = 100,
                embryo = 2,
                numberOfTopIndividuals = 1,
                numberOfEliteIndividuals = 2
            )
        )
      }
      println("Evolution took $time")
    }
    //    println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(generations))
  }
}

fun main(args: Array<String>) {
  runApplication<Runner>(*args)
}

@Configuration
class PackerConfiguration : EvolutionAutoConfiguration<Int, Int>() {

  @Bean
  fun individualCreator(): IndividualCreator<Int, Int> = object : IndividualCreator<Int, Int> {
    override fun invoke(p1: Int): () -> Individual<Int> = { SimpleIndividual(sequenceOf(Cell(1, 1, 1))) }
  }

  @Bean
  fun singleChildRecombinator() =
      object : Recombinator<Organism<Int>, Individual<Int>> {
        override fun invoke(p1: Pair<Organism<Int>, Organism<Int>>): Sequence<Individual<Int>> {
          return sequenceOf(SimpleIndividual(emptySequence()))
        }

      }

  @Bean
  fun twoChildrenRecombinator() =
      object : Recombinator<Organism<Int>, Individual<Int>> {
        override fun invoke(p1: Pair<Organism<Int>, Organism<Int>>): Sequence<Individual<Int>> {
          return sequenceOf(SimpleIndividual(emptySequence()), SimpleIndividual(emptySequence()))
        }

      }

  @Bean
  fun mutator() = object : Mutator<Int, Int> {
    override fun invoke(p1: Organism<Int>, p2: Int) {

    }
  }

  @Bean
  fun fitnessTester() = object : (Individual<*>) -> TranslatedFitness<*> {
    override fun invoke(p1: Individual<*>): TranslatedFitness<*> {
      return SomeTranslatedFitness(object : FitnessTranslator<SomeTranslatedFitness, Double> {
        override fun apply(t: SomeTranslatedFitness): Double {
          return 2.0
        }

      },
          object : FitnessTranslator<SomeTranslatedFitness, String> {
            override fun apply(t: SomeTranslatedFitness): String {
              return "what what"
            }
          })
    }
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
  fun contextMapper() = object : Evolution.ContextMapper<Int> {
    override fun invoke(p1: Evolution.Context<Int>) =
        GenerationContext(embryo = 1, chromosomeSize = 1, generationSize = 5, numberOfEliteIndividuals = 1)
  }
}