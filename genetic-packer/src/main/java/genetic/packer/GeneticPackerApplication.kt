package genetic.packer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["genetic"])
class GeneticPackerApplication

fun main(args: Array<String>) {
  runApplication<GeneticPackerApplication>(*args)
}