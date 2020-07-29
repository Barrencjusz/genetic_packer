package pl.piteron.genetic.packer

import genetic.Evolution
import genetic.EvolutionAutoConfiguration
import genetic.api.individual.Cell
import genetic.api.individual.Individual
import genetic.api.individual.IndividualCreator
import genetic.api.individual.impl.SimpleIndividual
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class Runner(
    private val evolution: Evolution<Int, Int>
) : CommandLineRunner {

  override fun run(vararg args: String) {
    evolution(
        Evolution.Context(
            numberOfGenerations = 1000,
            generationSize = 10,
            embryo = 2,
            numberOfTopIndividuals = 1,
            numberOfEliteIndividuals = 2
        )
    )
  }
}

fun main(args: Array<String>) {
  runApplication<Runner>(*args)
}

@Configuration
class PackerConfiguration : EvolutionAutoConfiguration<Int, Int>() {

  @Bean
  fun individualCreator(): IndividualCreator<Int, Int> = object : IndividualCreator<Int, Int> {
    override fun invoke(p1: Int): () -> Individual<Int> {
      return { SimpleIndividual(sequenceOf(Cell(1, 1, 1))) }
    }
  }
}