package genetic.api.fitness

interface Fitness {

  val score: Double

  fun explain(): String
}