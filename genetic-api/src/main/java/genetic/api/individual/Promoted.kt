package genetic.api.individual

import genetic.api.elitism.EliteAcknowledged
import genetic.api.elitism.Promoter
import genetic.api.individual.impl.Elite

interface Promoted<T> {

  fun <M> promote(promoter: M): Elite<T> where M : Promoter, M : EliteAcknowledged
}