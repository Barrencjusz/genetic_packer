package genetic.api.individual.impl

import genetic.api.elitism.EliteAcknowledged
import genetic.api.elitism.Promoter
import genetic.api.fitness.Fitness
import genetic.api.individual.Organism
import genetic.api.individual.Promoted
import genetic.api.individual.Rated

open class RatedIndividual<T>(
    override val fitness: Fitness,
    private val organism: Organism<T>
) : Organism<T> by organism, Rated, Promoted<T> {

  override fun <M> promote(promoter: M) where M : Promoter, M : EliteAcknowledged = promoter(this)
}