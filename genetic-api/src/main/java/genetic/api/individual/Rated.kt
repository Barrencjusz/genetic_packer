package genetic.api.individual

import genetic.api.fitness.Fitness

interface Rated {

  val fitness: Fitness
}